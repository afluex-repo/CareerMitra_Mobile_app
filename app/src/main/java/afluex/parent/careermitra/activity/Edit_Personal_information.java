package afluex.parent.careermitra.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditPersonalInformationBinding;
import afluex.parent.careermitra.model.response.ResponseCityState;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.getProfileDetail.ResponseViewProfile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Calendar;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Personal_information extends BaseActivity {



    String gender = "Male", cityId = "", stateId = "";
   EditPersonalInformationBinding binding;
   CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       toolbar=binding.toolbar;
       toolbar.tvTitle.setText(R.string.update_personal_information);

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioFemale)
                gender = "Female";
            else gender = "Male";
        });

        binding.tvDob.setOnClickListener(v -> {
            datePicker();
            binding.tvDob.setText("");
        });

        binding.etPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.etPinCode.getText().toString().trim().length() > 0) {
                    if (binding.etPinCode.getText().toString().trim().length() == 6)
                        getPincode();
                }
            }
        });

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            ViewProfile();
        else showMessage(getString(R.string.alert_internet));

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
                    if (validateForm())
                        updatePersonalDetails();
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });
    }



    private void updatePersonalDetails() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("cityId", cityId);
        object.addProperty("dob", binding.tvDob.getText().toString().trim());
        object.addProperty("email", binding.etEmail.getText().toString().trim());
        object.addProperty("fatherName", binding.etFatherName.getText().toString().trim());
        object.addProperty("gender", gender);
        object.addProperty("firstName", binding.etFirstName.getText().toString().trim());
        object.addProperty("lastName", binding.etLastName.getText().toString().trim());
        object.addProperty("mobile2", binding.etMobileNumber.getText().toString().trim());
        object.addProperty("motherName", binding.etMotherName.getText().toString().trim());
        object.addProperty("pinCode", binding.etPinCode.getText().toString().trim());
        object.addProperty("permanentAddress", binding.etPermanentAddress.getText().toString().trim());
        object.addProperty("address", binding.etAddress.getText().toString().trim());
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.updatePersonalDetail(bodyParam(object));
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

    private boolean validateForm() {
        if (!Utils.isEmailValid(binding.etEmail.getText().toString().trim())) {
            showMessage(getResources().getString(R.string.valid_mail));
            return false;
        } else if (binding.etFatherName.getText().toString().trim().length() < 2) {
            showMessage(getString(R.string.enter_father_name));
            return false;
        } else if (binding.etMotherName.getText().toString().trim().length() < 2) {
            showMessage(getString(R.string.enter_mother_name));
            return false;
        }
        return true;
    }

    private void datePicker() {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            binding.tvDob.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public void getPincode() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Pincode", binding.etPinCode.getText().toString().trim());
         if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");


        Call<ResponseCommon> call = apiServices.GetPincode(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseCityState state;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        state = gson.fromJson(paramResponse, ResponseCityState.class);
                        binding.etState.setText(state.getState());
                        binding.etCity.setText(state.getCity());
                        cityId = state.getCityId() + "";
                        stateId = state.getStateId() + "";
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

    private void ViewProfile() {
        showLoading();
        JsonObject object = new JsonObject();

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


        Call<ResponseCommon> call = apiServices.GetViewProfile(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseViewProfile responseViewProfile;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseViewProfile = gson.fromJson(paramResponse, ResponseViewProfile.class);
                        binding.etFirstName.setText(responseViewProfile.getFirstName());
                        binding.etLastName.setText(responseViewProfile.getLastName());
                        binding.etEmail.setText(responseViewProfile.getEmail());
                        binding.etMobileNumber.setText(responseViewProfile.getMobileNo());
                        binding.etFatherName.setText(responseViewProfile.getFatherName());
                        binding.etMotherName.setText(responseViewProfile.getMotherName());
                        binding.etAddress.setText(responseViewProfile.getAddress());
                        binding.etCity.setText(responseViewProfile.getCity());
                        binding.etState.setText(responseViewProfile.getState());
                        binding.etPinCode.setText(responseViewProfile.getPinCode());
                        binding.tvDob.setText(responseViewProfile.getDOB());
                        if (responseViewProfile.getGender().equalsIgnoreCase("Female"))
                            binding.radioFemale.setChecked(true);
                        else binding.radioMale.setChecked(true);
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
