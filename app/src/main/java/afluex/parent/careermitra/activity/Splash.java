package afluex.parent.careermitra.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.GpsTracker;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.SplashBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.ResponseRegistration;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends BaseActivity {

SplashBinding binding;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        try {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//            } else {
//        getLocation();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.delay_zoom_in);
        binding.imageView.startAnimation(aniSlide);

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.tvVersion.setText(getString(R.string.version) + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(() -> {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                checkUpdate();
            } else {
                showMessage(getString(R.string.alert_internet));
            }
        }, 1500);
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(Splash.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            PreferencesManager.getInstance(context).setLatitude(latitude + "");
            PreferencesManager.getInstance(context).setLongitude(longitude + "");
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void checkLogin() {
//        getLocation();
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat",PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long",PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");

        Log.e("EDFVKH",Utils.getAndroidDeviceId(context));
        Log.e("EDFVKH",PreferencesManager.getInstance(context).getLatitude());
        Log.e("EDFVKH",PreferencesManager.getInstance(context).getLongitude());


        Call<ResponseCommon> call = apiServices.CheckLogIn(bodyParam(object));
        Log.e("EDFVKH",""+bodyParam(object));

        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseRegistration registration;
//                Log.e("EDFVKH111",""+ response.body().getStatusCode());
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
                        if (PreferencesManager.getInstance(context).getIsFirstTimeLaunch())
                            goToActivityWithFinish(context, LanguageSeletion.class, null);
                        else
                            goToActivityWithFinish(context, Login.class, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Erghs",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                hideLoading();
//                if (PreferencesManager.getInstance(context).getIsFirstTimeLaunch())
//                    goToActivityWithFinish(context, LanguageSeletion.class, null);
//                else
//                    goToActivityWithFinish(context, Login.class, null);
                Log.e("EDFVKH123",""+ t.getMessage());

            }
        });
    }

    private AppUpdater updater;

    private void checkUpdate() {
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", "=" + update.getLatestVersionCode());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", "=" + update.getUrlToDownload());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable) {
                            if (updater == null) {
                                updater = new AppUpdater(context).setDisplay(Display.DIALOG);
                                updater.setContentOnUpdateAvailable("Update " + update.getLatestVersion() + " is available to download. Download the latest version of CareerMitra to get latest " +
                                        "features, improvements and bug fixes.");
                                updater.setButtonDoNotShowAgain("");
                                updater.setButtonDismiss("");
                                updater.setCancelable(false);
                            }
                            updater.start();
                        } else {
                            checkLogin();
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();
    }

}