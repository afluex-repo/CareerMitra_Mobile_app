package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditAboutCompanyBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.Objects;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_About extends BaseActivity {

EditAboutCompanyBinding binding;
CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditAboutCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar=binding.toolbar;


        toolbar.tvTitle.setText(R.string.about_yur);

        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (binding.etAboutCompany.getText().toString().length() > 3)
                        updateUserAbout();
                    else
                        showMessage(getString(R.string.write_something_about));
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });
    }



    private void updateUserAbout() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("about", Objects.requireNonNull(binding.etAboutCompany.getText()).toString());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.UpdateProfileAbout(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200))
                        onBackPressed();
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