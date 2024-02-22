package afluex.parent.careermitra.activity;

import static afluex.parent.careermitra.app.AppConfig.PAYLOAD_BUNDLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ResetPasswordBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends BaseActivity {

    ResetPasswordBinding binding;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);

        binding.btnnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (Validation())
                        ResetPassword();
                } else showMessage(R.string.alert_internet);
            }
        });
    }




    private void ResetPassword() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", bundle.getString("mobile"));
        object.addProperty("tempPassword", binding.etTempPassword.getText().toString());
        object.addProperty("password", binding.etNewPassword.getText().toString());

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.ResetPassword(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        goToActivityWithFinish(context, Login.class, null);
                    } else {
                        showMessage(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private boolean Validation() {
        if (binding.etTempPassword.getText().toString().length() == 0) {
            binding.etTempPassword.setError("Please enter Temp password");
            return false;
        } else if (!isValidPassword(binding.etNewPassword.getText().toString().trim())) {
            createInfoDialog(context, getResources().getString(R.string.password), getResources().getString(R.string.charPswd), false);
            return false;
        } else if (binding.etNewPassword.getText().toString().trim().equals(binding.etNewPassword.getText().toString().trim())) {
            binding.etNewPassword.setError("New Password could not be same as old password");
            return false;
        } else if (!binding.etNewPassword.getText().toString().equals(binding.etCnfPassword.getText().toString())) {
            binding.etCnfPassword.setError("Password not matched");
            binding.etNewPassword.setError("Password not matched");
            return false;
        }
        return true;
    }
}
