package afluex.parent.careermitra.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.GpsTracker;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityLoginBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.ResponseRegistration;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends BaseActivity {

    ActivityLoginBinding binding;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validateForm())
                        loginUser();
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });

        binding.tvGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityWithFinish(context, SignUp.class, null);
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, ForgotPassword.class, null);
            }
        });
        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityWithFinish(context, ContainerActivity.class, null);
            }
        });
    }



    public void getLocation() {
        gpsTracker = new GpsTracker(Login.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            PreferencesManager.getInstance(context).setLatitude(latitude + "");
            PreferencesManager.getInstance(context).setLongitude(longitude + "");
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void loginUser() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", binding.etEmail.getText().toString());
        object.addProperty("password", binding.etPassword.getText().toString());
        object.addProperty("fcmId", "");
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.Login(bodyParam(object));
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
                Log.e("EDFVKH",t.toString());
            }
        });
    }

    private boolean validateForm() {
        if (binding.etEmail.getText().toString().trim().length() != 10) {
            showMessage(getString(R.string.registered_mobile));
            return false;
        } else if (binding.etPassword.getText().toString().length() < 6) {
            showMessage(getString(R.string.enter_pswd));
            return false;
        }
        return true;
    }
}