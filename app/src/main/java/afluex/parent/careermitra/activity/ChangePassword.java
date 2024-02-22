package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ChnagePasswordBinding;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends BaseActivity {


    Bundle bundle;

    ChnagePasswordBinding binding;

    CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChnagePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=binding.toolbar;

        toolbar.tvTitle.setText(R.string.change_password);

        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (Validation())
                        ChangePassword();
                } else showMessage(R.string.alert_internet);
            }
        });
    }

  /*  @OnClick(R.id.btnn_submit)
    public void onClick() {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (Validation())
                ChangePassword();
        } else showMessage(R.string.alert_internet);
    }*/

    private void ChangePassword() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("oldPassword", binding.etOldPassword.getText().toString());
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


        Call<ResponseCommon> call = apiServices.ChangePassword(bodyParam(object));
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
        if (binding.etOldPassword.getText().toString().length() == 0) {
            binding.etOldPassword.setError(getString(R.string.enter_the_olde_password));
            return false;
        } else if (isValidPassword(binding.etNewPassword.getText().toString().trim())) {
            createInfoDialog(context, getResources().getString(R.string.password), getResources().getString(R.string.charPswd), false);
            return false;
        } else if (binding.etNewPassword.getText().toString().trim().equals(binding.etOldPassword.getText().toString().trim())) {
            binding.etNewPassword.setError(getString(R.string.new_password_not_be_same_sa_old_password));
            return false;
        } else if (!binding.etNewPassword.getText().toString().equals(binding.etCnfPassword.getText().toString())) {
            binding.etCnfPassword.setError(getString(R.string.password_not_matched));
            binding.etNewPassword.setError(getString(R.string.password_not_matched));
            return false;
        }
        return true;
    }



}
