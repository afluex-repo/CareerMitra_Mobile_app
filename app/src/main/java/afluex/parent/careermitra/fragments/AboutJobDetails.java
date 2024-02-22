package afluex.parent.careermitra.fragments;

import static afluex.parent.careermitra.app.AppConfig.PAYLOAD_BUNDLE;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.FragmentJobDiscriptionBinding;
import afluex.parent.careermitra.model.ResponseJobDetails;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
AboutJobDetails extends BaseActivity {

    FragmentJobDiscriptionBinding binding;

    CustomActionBarBinding toolbar;

    Bundle bundle;
    ArrayList<String> arrayData = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentJobDiscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      toolbar=binding.toolbar;


        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
       toolbar.tvTitle.setText(getResources().getString(R.string.job_details));

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            ViewJobDetails();
        } else showMessage(getResources().getString(R.string.alert_internet));


        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnApplayJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                    createLoginDialog(context, getResources().getString(R.string.login_first_time), getString(R.string.login_continue));
                else
                    ApplyJobs();
            }
        });

    }


    public void ViewJobDetails() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", bundle.getString("jobId"));
         if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetJobDetails(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseJobDetails responseJobDetails;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseJobDetails = gson.fromJson(paramResponse, ResponseJobDetails.class);
                       if (TextUtils.isEmpty(responseJobDetails.getCompanyName())||TextUtils.isEmpty(responseJobDetails.getAbout()))
                        {
                            binding.tvCompanyName.setVisibility(View.GONE);
                            binding.tvAboutCompany.setVisibility(View.GONE);
                            //tvJobLocation.setVisibility(View.GONE);
                        }

                        //tv_Mobile.setText(arrayData.add(responseJobDetails.getSkill()));

                        binding.tvCompanyName.setText(responseJobDetails.getCompanyName());
                        binding.tvJobHighlights.setText(responseJobDetails.getTitle());
                        binding.tvExperienceYear.setText(getString(R.string.experience)+"("+(responseJobDetails.getExperienceMin() + " - " + responseJobDetails.getExperienceMax())+")"+"");
                        binding.tvJobLocation.setText(responseJobDetails.getCity());
                        binding.tvDescribAboutJob.setText(responseJobDetails.getDescription());
                        binding.tvSalary.setText(responseJobDetails.getSalaryMin() +" - "+responseJobDetails.getSalaryMax());
                        binding.tvEmploymenttype.setText(responseJobDetails.getJobType());
                        binding.Carpenter.setText(responseJobDetails.getJobRole());
                       binding.tvEmail.setText(responseJobDetails.getEmail());
                        binding.tvAddress.setText(responseJobDetails.getLocation());
                        binding.tvMobileNo.setText(responseJobDetails.getContact());
                        binding.tvJobLocation.setText(responseJobDetails.getCity());
                        binding.tvPostedJobDays.setText(getString(R.string.posted_job)+responseJobDetails.getTime());


                        binding.tvAboutCompany.setText(responseJobDetails.getAbout());
                        binding.tvIndustryType.setText(responseJobDetails.getDepartment());
                        binding.tvVacancies.setText(responseJobDetails.getNoOfVacancies() + " Vacancy ");
                        binding.tvEducation.setText(responseJobDetails.getQualification());
                        showMessage(response.body().getMessage());

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

  /*  @OnClick(R.id.btn_applay_job)
    public void onClick() {
        if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
            createLoginDialog(context, "Login First", "Please Login to continue!");
        else
            ApplyJobs();
    }*/

    private void ApplyJobs() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("jobId", bundle.getString("jobId"));


        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.ApplyJobs(bodyParam(object));
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
