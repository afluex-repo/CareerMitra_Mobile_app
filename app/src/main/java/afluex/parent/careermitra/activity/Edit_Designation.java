package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditDesignationNameBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Designation extends BaseActivity {


EditDesignationNameBinding binding;
CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        toolbar=binding.toolbar;
        

       toolbar.tvTitle.setText(R.string.update_company_name);

       toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });

       binding.btnSaved.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (NetworkUtils.getConnectivityStatus(context) != 0) {
                   if (binding.etDesignationName.getText().toString().length() > 3 &&
                           binding.etCompanyLocationName.getText().toString().length() > 3)
                       updateCompany();
                   else
                       showMessage(getString(R.string.enter_company_designation));
               } else showMessage(getResources().getString(R.string.alert_internet));
           }
       });
    }



    private void updateCompany() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("companyName", binding.etCompanyLocationName.getText().toString());
        object.addProperty("designation", binding.etDesignationName.getText().toString());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.updateCompanyDetails(bodyParam(object));
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