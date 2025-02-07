package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditEducationBinding;
import afluex.parent.careermitra.model.response.Course.Course;
import afluex.parent.careermitra.model.response.Course.ResponseCourse;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.List;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Education extends BaseActivity {



    String courseId = "";
    PopupMenu courseMenu;
    List<Course> coursesList = new ArrayList<>();

    EditEducationBinding binding;
    CustomActionBarBinding toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar=binding.toolbar;

       toolbar.tvTitle.setText(getResources().getString(R.string.education_qualification));

        courseMenu = new PopupMenu(context, binding.etCourse);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCourses();
        } else showMessage(getResources().getString(R.string.alert_internet));

        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.etCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseMenu.setOnMenuItemClickListener(item -> {
                    int position = item.getOrder();
                    binding.etCourse.setText(coursesList.get(position).getName());
                    courseId = (coursesList.get(position).getId());
                    return true;
                });
                courseMenu.show();
            }
        });

        binding.etPassingYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseYearOnly();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        updateEducation();
                    else showMessage(getResources().getString(R.string.alert_internet));
                }
            }
        });
    }



    private void chooseYearOnly() {
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> binding.etPassingYear.setText(Integer.toString(selectedYear)), 2008, 0);
        builder.showYearOnly()
//                .setYearRange(1990, 2030)
                .build()
                .show();
    }

    private void updateEducation() {
        showLoading();
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        JsonObject eobject = new JsonObject();
        eobject.addProperty("CourseId", courseId);
        eobject.addProperty("YearOfPassing", binding.etPassingYear.getText().toString().trim());
        eobject.addProperty("College", binding.etCollege.getText().toString().trim());
        array.add(eobject);
        object.add("educations", array);
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


        Call<ResponseCommon> call = apiServices.updateEducation(bodyParam(object));
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

    private void getCourses() {
        showLoading();
        JsonObject object = new JsonObject();
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");


        Call<ResponseCommon> call = apiServices.GetCourses(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseCourse course;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        course = gson.fromJson(paramResponse, ResponseCourse.class);
                        coursesList = course.getCourses();
                        for (int i = 0; i < coursesList.size(); i++) {
                            courseMenu.getMenu().add(0, 0, i, coursesList.get(i).getName());
                        }
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

    private boolean validateData() {
        if (courseId.length() == 0) {
            showMessage(getString(R.string.select_crrosse));
            return false;
        } else if (binding.etCollege.getText().toString().trim().length() < 3) {
            showMessage(getString(R.string.enter_collage_name));
            return false;
        } else if (binding.etPassingYear.getText().toString().trim().length() < 3) {
            showMessage(getString(R.string.select_passing_year));
            return false;
        }
        return true;
    }
}
