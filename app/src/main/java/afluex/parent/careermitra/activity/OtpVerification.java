package afluex.parent.careermitra.activity;

import static afluex.parent.careermitra.app.AppConfig.PAYLOAD_BUNDLE;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityOtpBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.ResponseRegistration;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerification extends BaseActivity {

ActivityOtpBinding binding;
    Bundle bundle;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (bundle != null) {
            binding.verficationText.setText(getResources().getString(R.string.enter_the_verification_code) + " " + bundle.getString("mobile"));
        }

        binding.tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateOTP();
            }
        });

        binding.btnOtpVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etPinEntry.getText().toString().trim().length() == 6) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        validateOTP();
                    else showMessage(getResources().getString(R.string.alert_internet));
                } else showMessage(getString(R.string.otp));
            }
        });
    }


    private void GenerateOTP() {
        hideKeyboard();
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("mobileNo", bundle.getString("mobile"));
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GenerateOTP(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        binding.tvResendOtp.setVisibility(View.GONE);
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



    private void validateOTP() {
        hideKeyboard();
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("otp", binding.etPinEntry.getText().toString().trim());
        object.addProperty("mobileNo", bundle.getString("mobile"));
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


        Call<ResponseCommon> call = apiServices.ValidateOTP(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        new Handler().postDelayed(() -> registerUser(), 200);
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

    private void registerUser() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("firstName", bundle.getString("fname"));
        object.addProperty("lastName", bundle.getString("lname"));
        object.addProperty("mobileNo", bundle.getString("mobile"));
        object.addProperty("email", bundle.getString("email"));
        object.addProperty("password", bundle.getString("password"));
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("FCMId", "");

        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("deviceType", "");
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.Register(bodyParam(object));

        Log.e("bodyPAGHFGD",bodyParam(object).toString());
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseRegistration registration;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        registration = gson.fromJson(paramResponse, ResponseRegistration.class);
                        PreferencesManager.getInstance(context).setUserid(registration.getId());
                        PreferencesManager.getInstance(context).setFirstName(registration.getFirstName());
                        PreferencesManager.getInstance(context).setLastName(registration.getLastName());
                        PreferencesManager.getInstance(context).setEmail(registration.getEmail());
                        PreferencesManager.getInstance(context).setMobileno(registration.getMobileNo());
                        PreferencesManager.getInstance(context).setImage(registration.getPhoto());
                        goToActivityWithFinish(context, ContainerActivity.class, null);
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
}