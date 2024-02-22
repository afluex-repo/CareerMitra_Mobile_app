package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditExperienceBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.whiteelephant.monthpicker.MonthPickerDialog;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Experience extends BaseActivity {

    private boolean isWorking = false;

    EditExperienceBinding binding;
    CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditExperienceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=binding.toolbar;
       toolbar.tvTitle.setText(R.string.update_exp);

        binding.cbCurrnetlyWorking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.tvEndDate.setVisibility(View.GONE);
                isWorking = true;
            } else {
                binding.tvEndDate.setVisibility(View.VISIBLE);
                isWorking = false;
            }
        });

        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        updateExperience();
                    else showMessage(getResources().getString(R.string.alert_internet));
                }
            }
        });

        binding.tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseYearOnly(binding.tvStartDate);
            }
        });
        binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseYearOnly(binding.tvEndDate);
            }
        });


    }



    private void chooseYearOnly(TextView et) {
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> et.setText(Integer.toString(selectedYear)), 2008, 0);
        builder.showYearOnly()
//                .setYearRange(1990, 2030)
                .build()
                .show();
    }

    private void updateExperience() {
        showLoading();
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        JsonObject eobject = new JsonObject();
        eobject.addProperty("Company", binding.etCompanyName.getText().toString().trim());
        eobject.addProperty("Designation", binding.etDesignationName.getText().toString().trim());
        eobject.addProperty("YearFrom", binding.tvStartDate.getText().toString().trim());
        eobject.addProperty("YearTo", binding.tvEndDate.getText().toString().trim());
        eobject.addProperty("IsCurrent", isWorking);
        array.add(eobject);
        object.add("experiences", array);
        //object.addProperty("CourseId",jsonArray);
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");

        // object.addProperty("educations", new Gson().toJson(jsonArray));


        Call<ResponseCommon> call = apiServices.UpdateProfileExperience(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        onBackPressed();
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

    private boolean validateData() {
        if (binding.etDesignationName.getText().toString().trim().length() < 3) {
            showMessage(getString(R.string.enter_designation));
            return false;
        } else if (binding.etCompanyName.getText().toString().trim().length() < 3) {
            showMessage(getString(R.string.enter_company_name));
            return false;
        } else if (binding.tvStartDate.getText().toString().length() == 0) {
            showMessage(getString(R.string.select__start_year));
            return false;
        } else if (!isWorking) {
            if (binding.tvEndDate.getText().toString().length() == 0) {
                showMessage(getString(R.string.select_end_year));
                return false;
            }
        }
        return true;
    }
}
