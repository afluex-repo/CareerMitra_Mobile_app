package afluex.parent.careermitra.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.AdapterDepartments;
import afluex.parent.careermitra.adapter.AdapterJobList;
import afluex.parent.careermitra.adapter.AdapterQualification;
import afluex.parent.careermitra.adapter.SkillAdapterSearchItem;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.LoggerUtil;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.FragmentJoblistBinding;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.SearchJob.ResponseSearchJob;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.responseFilters.ResponseFilters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.innovattic.rangeseekbar.RangeSeekBar;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListFragment extends BaseFragment implements JobDetailsListener {

    public static JsonArray skillIds = new JsonArray();
    public static JsonArray cityIds = new JsonArray();
    public static JsonArray courseId = new JsonArray();
    public static JsonArray categoryId = new JsonArray();
    String minSalary = "", maxSalary = "";

    public static String searchKeyWord = "";
    Dialog deptDialog, qualificationDialog, SkillesDilogs, SalaryDilog;
    ResponseFilters responseFilters;

    FragmentJoblistBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentJoblistBinding.inflate(inflater,container,false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvcListJob.setLayoutManager(layoutManager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            GetFilters();
            GetJobDetails();
        } else showMessage(getResources().getString(R.string.alert_internet));
        binding.tvDept.setOnClickListener(v -> opendeptDialog());
        binding.tvQualifi.setOnClickListener(v -> openQualificationDialog());
        binding.tvSkill.setOnClickListener(v -> openSkillDilog());

        binding.tvSalary.setOnClickListener(v -> openSalery());

        return binding.getRoot();
    }

    public void GetJobDetails() {
        showLoading();

        JsonObject object = new JsonObject();
        object.add("skillId", (JsonElement) skillIds);
        object.add("cityId", (JsonElement) cityIds);
        object.add("categoryId", (JsonElement) categoryId);
        object.add("courseId", (JsonElement) courseId);

        object.addProperty("address", "");
        object.addProperty("city", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("deviceType", "");
        object.addProperty("domain", "");

        object.addProperty("employerId", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("ip", "");
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("os", "");

        object.addProperty("page", "");
        object.addProperty("salaryMax", maxSalary);
        object.addProperty("salaryMin", minSalary);
        object.addProperty("searchTerm", searchKeyWord);
        object.addProperty("sortBy", "");
        object.addProperty("userAgent", "");


        Call<ResponseCommon> call = apiServices.GetJobList(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseSearchJob responseSearchJob;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseSearchJob = gson.fromJson(paramResponse, ResponseSearchJob.class);

                        AdapterJobList adapter = new AdapterJobList(responseSearchJob.getListJob(), context, JobListFragment.this);
                        binding.rvcListJob.setAdapter(adapter);
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

    @Override
    public void openJobDetails(String jobId) {
        Bundle bundle = new Bundle();
        bundle.putString("jobId", jobId);
        goToActivity(AboutJobDetails.class, bundle);
    }

    private void opendeptDialog() {
        deptDialog = new Dialog(context);
        deptDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deptDialog.setContentView(R.layout.dialog_department_filters);

        RecyclerView rv_dept = deptDialog.findViewById(R.id.rv_dept);
        Button btn_apply = deptDialog.findViewById(R.id.btn_apply);
        Button btn_cancel = deptDialog.findViewById(R.id.btn_cancel);
        Button btn_clear = deptDialog.findViewById(R.id.btn_clear);

        btn_cancel.setOnClickListener(v -> deptDialog.dismiss());
        AdapterDepartments adapterDepartments = new AdapterDepartments(responseFilters.getDepartments(), context);
        rv_dept.setLayoutManager(new LinearLayoutManager(context));
        rv_dept.setAdapter(adapterDepartments);

        btn_apply.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getDepartments().size(); i++) {
                for (int j = 0; j < responseFilters.getDepartments().get(i).getCategories().size(); j++) {
                    if (responseFilters.getDepartments().get(i).getCategories().get(j).isSelected()) {
                        categoryId.add(responseFilters.getDepartments().get(i).getCategories().get(j).getCategoryId());
                    }
                }
            }
            GetJobDetails();
            deptDialog.dismiss();
        });

        btn_clear.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getDepartments().size(); i++) {
                for (int j = 0; j < responseFilters.getDepartments().get(i).getCategories().size(); j++) {
                    if (responseFilters.getDepartments().get(i).getCategories().get(j).isSelected()) {
                        responseFilters.getDepartments().get(i).getCategories().get(j).setSelected(false);
                    }
                }
            }
            categoryId = new JsonArray();
            GetJobDetails();
            deptDialog.dismiss();
        });

        deptDialog.setCancelable(false);
        deptDialog.setCanceledOnTouchOutside(false);
        deptDialog.show();
    }


    private void openQualificationDialog() {
        qualificationDialog = new Dialog(context);
        qualificationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        qualificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        qualificationDialog.setContentView(R.layout.dialog_qualification);

        RecyclerView rv_dept = qualificationDialog.findViewById(R.id.rv_dept);
        Button btn_apply = qualificationDialog.findViewById(R.id.btn_apply);
        Button btn_cancel = qualificationDialog.findViewById(R.id.btn_cancel);
        Button btn_clear = qualificationDialog.findViewById(R.id.btn_clear);

        btn_cancel.setOnClickListener(v -> qualificationDialog.dismiss());
        AdapterQualification adapterDepartments = new AdapterQualification(responseFilters.getQualification(), context);
        rv_dept.setLayoutManager(new LinearLayoutManager(context));
        rv_dept.setAdapter(adapterDepartments);

        btn_apply.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getQualification().size(); i++) {
                if (responseFilters.getQualification().get(i).isSelected()) {
                    courseId.add(responseFilters.getQualification().get(i).getId());
                }
            }
            GetJobDetails();
            qualificationDialog.dismiss();
        });

        btn_clear.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getQualification().size(); i++) {
                if (responseFilters.getQualification().get(i).isSelected()) {
                    responseFilters.getQualification().get(i).setSelected(false);
                }
            }
            courseId = new JsonArray();
            GetJobDetails();
            qualificationDialog.dismiss();
        });

        qualificationDialog.setCancelable(false);
        qualificationDialog.setCanceledOnTouchOutside(false);
        qualificationDialog.show();
    }

    public void GetFilters() {
        Call<ResponseCommon> call = apiServices.GetFilters();
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();


                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseFilters = gson.fromJson(paramResponse, ResponseFilters.class);
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

    private void openSkillDilog() {
        SkillesDilogs = new Dialog(context);
        SkillesDilogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SkillesDilogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SkillesDilogs.setContentView(R.layout.dilog_skills);

        RecyclerView rv_skills = SkillesDilogs.findViewById(R.id.rv_skills);
        Button btn_apply = SkillesDilogs.findViewById(R.id.btn_apply);
        Button btn_cancel = SkillesDilogs.findViewById(R.id.btn_cancel);
        Button btn_clear = SkillesDilogs.findViewById(R.id.btn_clear);

        btn_cancel.setOnClickListener(v -> SkillesDilogs.dismiss());
        SkillAdapterSearchItem adapterDepartments = new SkillAdapterSearchItem(responseFilters.getSkills(), context);
        rv_skills.setLayoutManager(new LinearLayoutManager(context));
        rv_skills.setAdapter(adapterDepartments);

        btn_apply.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getSkills().size(); i++) {
                if (responseFilters.getSkills().get(i).isSelected()) {
                    courseId.add(responseFilters.getSkills().get(i).getId());
                }
            }
            GetJobDetails();
            SkillesDilogs.dismiss();
        });

        btn_clear.setOnClickListener(v -> {
            for (int i = 0; i < responseFilters.getSkills().size(); i++) {
                if (responseFilters.getSkills().get(i).isSelected()) {
                    responseFilters.getSkills().get(i).setSelected(false);
                }
            }
            courseId = new JsonArray();
            GetJobDetails();
            SkillesDilogs.dismiss();
        });

        SkillesDilogs.setCancelable(false);
        SkillesDilogs.setCanceledOnTouchOutside(false);
        SkillesDilogs.show();
    }

    private void openSalery() {
        SalaryDilog = new Dialog(context);
        SalaryDilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SalaryDilog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SalaryDilog.setContentView(R.layout.dailog_salary);
        TextView textView = SalaryDilog.findViewById(R.id.textView3);

        RangeSeekBar double_range_seekbar = SalaryDilog.findViewById(R.id.double_range_seekbar);

        if (minSalary.length() == 0)
            double_range_seekbar.setMinRange(responseFilters.getSalary().getMin());
        else double_range_seekbar.setMinRange(Integer.parseInt(minSalary));

        if (maxSalary.length() == 0)
            double_range_seekbar.setMax(responseFilters.getSalary().getMax());
        else double_range_seekbar.setMax(Integer.parseInt(maxSalary));

        textView.setText(String.valueOf(responseFilters.getSalary().getMin()) + " to " + String.valueOf(responseFilters.getSalary().getMax()));

        double_range_seekbar.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {
            }

            @Override
            public void onStoppedSeeking() {
            }

            @Override
            public void onValueChanged(int i, int i1) {
                minSalary = String.valueOf(i);
                maxSalary = String.valueOf(i1);
                textView.setText(String.valueOf(i) + " to " + String.valueOf(i1));
                LoggerUtil.logItem(String.valueOf(responseFilters.getSalary().getMin() + responseFilters.getSalary().getMax()));
            }
        });

        Button btn_apply = SalaryDilog.findViewById(R.id.btn_apply);
        Button btn_cancel = SalaryDilog.findViewById(R.id.btn_cancel);
        Button btn_clear = SalaryDilog.findViewById(R.id.btn_clear);

        btn_cancel.setOnClickListener(v -> SalaryDilog.dismiss());

        btn_apply.setOnClickListener(v -> {
            GetJobDetails();
            SalaryDilog.dismiss();
        });

        btn_clear.setOnClickListener(v -> {
            minSalary = "";
            maxSalary = "";
            GetJobDetails();
            SalaryDilog.dismiss();
        });

        SalaryDilog.setCancelable(false);
        SalaryDilog.setCanceledOnTouchOutside(false);
        SalaryDilog.show();
    }

}



