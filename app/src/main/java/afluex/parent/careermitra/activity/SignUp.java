package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.LoggerUtil;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivitySignupBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.validateMobile.ResponseCheckMobile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;






public class SignUp extends BaseActivity {


    ActivitySignupBinding binding;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySignupBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        binding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.etMobile.getText().toString().trim().length() > 0) {
                    if (binding.etMobile.getText().toString().trim().length() == 10)
                        checkMobile();
                }
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validateForm()) {
                        try {
                            GenerateOTP();
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }

                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityWithFinish(context, Login.class, null);
            }
        });
    }



    private void GenerateOTP() throws UnsupportedEncodingException {
        hideKeyboard();
        showLoading();

        Log.e("GHfrtd",Utils.getAndroidDeviceId(context));
        Log.e("GHfrtd", PreferencesManager.getInstance(context).getLatitude());
        Log.e("GHfrtd",PreferencesManager.getInstance(context).getLongitude());
        JsonObject object = new JsonObject();
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("mobileNo", binding.etMobile.getText().toString().trim());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");



//        byte[] encodedBytes = java.util.Base64.getEncoder().encode(object)


        Call<ResponseCommon> call = apiServices.GenerateOTP(bodyParam(object));
        Log.e("BOsjhn",""+bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("fname", binding.etFirstname.getText().toString().trim());
                        bundle.putString("lname", binding.etLasttname.getText().toString().trim());
                        bundle.putString("mobile", binding.etMobile.getText().toString().trim());
                        bundle.putString("email", binding.etEmail.getText().toString().trim());
                        bundle.putString("password", binding.etPassword.getText().toString().trim());
                        goToActivityWithFinish(context, OtpVerification.class, bundle);

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

    private void checkMobile() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", binding.etMobile.getText().toString().trim());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("deviceType", "");
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.CheckMobileNo(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseCheckMobile responseGenerateOTP;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseGenerateOTP = gson.fromJson(paramResponse, ResponseCheckMobile.class);
                        LoggerUtil.logItem(responseGenerateOTP);
                        if (responseGenerateOTP.isIsExist())
                            createInfoDialog(context, getString(R.string.already), getString(R.string.already_mobile), true);
                    } else {
                        showMessage("Something went wrong!");
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

    private boolean validateForm() {
        if (binding.etMobile.getText().toString().trim().length() != 10) {
            showMessage(getString(R.string.valid_mobile));
            return false;
        } else if (binding.etFirstname.getText().toString().trim().length() < 2) {
            showMessage(getString(R.string.first_valid_name));
            return false;
        } else if (!Utils.isEmailValid(binding.etEmail.getText().toString().trim())) {
            showMessage(getString(R.string.valid_mail));
            return false;
        } else if (!(isValidPassword(binding.etPassword.getText().toString().trim()))) {
            createInfoDialog(context, getResources().getString(R.string.password), getResources().getString(R.string.charPswd), false);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        goToActivityWithFinish(context, Login.class, null);
        super.onBackPressed();
    }
}
