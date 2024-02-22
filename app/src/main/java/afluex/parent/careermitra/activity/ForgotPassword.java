package afluex.parent.careermitra.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import afluex.parent.careermitra.databinding.ForgotPasswordBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends BaseActivity {


ForgotPasswordBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (binding.etMobile.getText().toString().trim().length() == 10)
                        ForgotPassword();
                    else showMessage(getString(R.string.enter_valid_mobile_no));
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });
    }




    @SuppressLint("NotConstructor")
    private void ForgotPassword() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", binding.etMobile.getText().toString());

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.ForgotPassword(bodyParam(object));

        Log.e("FOFSC",""+bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", binding.etMobile.getText().toString().trim());
                        goToActivityWithFinish(context, ResetPassword.class, bundle);
                    }
                    showMessage(response.body().getMessage());

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
}