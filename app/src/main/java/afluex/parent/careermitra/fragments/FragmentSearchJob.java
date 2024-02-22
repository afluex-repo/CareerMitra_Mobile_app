package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.activity.ContainerActivity;
import afluex.parent.careermitra.activity.Profile;
import afluex.parent.careermitra.adapter.RecentJobAdapter;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.FragmentJobSearchBinding;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.responseCity.City;
import afluex.parent.careermitra.model.response.responseCity.ResponseCityName;
import afluex.parent.careermitra.model.response.responseRecentJob.ResponseJobList;
import afluex.parent.careermitra.model.response.skills.ResponseSkills;
import afluex.parent.careermitra.model.response.skills.SkillsItem;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearchJob extends BaseFragment implements JobDetailsListener {



    FragmentJobSearchBinding binding;

    //    Search
    PopupMenu skillsMenu, CityMenu;
    List<SkillsItem> skillsList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentJobSearchBinding.inflate(inflater,container,false);

        binding.seekbar.setVisibility(View.GONE);
        binding.imgProfile.setVisibility(View.GONE);

        binding.rvRecentjobs.setLayoutManager(new LinearLayoutManager(context));
        skillsMenu = new PopupMenu(context, binding.etSearchBySkilled);
        CityMenu = new PopupMenu(context, binding.etLocationSearch);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            GetJobDetails();
//            getSkills();
            getCity();
        } else showMessage(getResources().getString(R.string.alert_internet));

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobListFragment.searchKeyWord = binding.etSearchBySkilled.getText().toString().trim();
                ((ContainerActivity) context).ReplaceFragment(new JobListFragment(), getString(R.string.search_job_list));
            }
        });
         binding.profileConstrantlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(Profile.class, null);
            }
        });
         binding.etSearchBySkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillsMenu.setOnMenuItemClickListener(item -> {
                    int position = item.getOrder();
                    if (binding.etSearchBySkilled.getText().toString().trim().length() > 0)
                        binding.etSearchBySkilled.setText(binding.etSearchBySkilled.getText().toString().trim() + ", " + skillsList.get(position).getName());
                    else
                        binding.etSearchBySkilled.setText(skillsList.get(position).getName());
                    JobListFragment.skillIds.add(skillsList.get(position).getId());
                    return true;
                });
                skillsMenu.show();
            }
        });
         binding.etLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityMenu.setOnMenuItemClickListener(item -> {
                    int position = item.getOrder();
                    if (binding.etLocationSearch.getText().toString().trim().length() > 0)
                        binding.etLocationSearch.setText(binding.etLocationSearch.getText().toString().trim() + ", " + cityList.get(position).getName());
                    else
                        binding.etLocationSearch.setText(cityList.get(position).getName());
                    JobListFragment.cityIds.add(cityList.get(position).getId());
                    return true;
                });
                CityMenu.show();
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        JobListFragment.skillIds.clear();
        JobListFragment.cityIds = new JsonArray();
        JobListFragment.searchKeyWord = "";
        binding.etSearchBySkilled.setText("");
    }



    @Override
    public void openJobDetails(String jobId) {
        Bundle bundle = new Bundle();
        bundle.putString("jobId", jobId);
        goToActivity(AboutJobDetails.class, bundle);
    }

    private void getSkills() {
        JsonObject object = new JsonObject();
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");


        Call<ResponseCommon> call = apiServices.GetSkills(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseSkills skills;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        skills = gson.fromJson(paramResponse, ResponseSkills.class);
                        skillsList = skills.getSkills();
                        for (int i = 0; i < skillsList.size(); i++) {
                            skillsMenu.getMenu().add(0, 0, i, skillsList.get(i).getName());
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

    public void getCity() {
        JsonObject object = new JsonObject();
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "0");
        object.addProperty("SearchText", "L");

        Call<ResponseCommon> call = apiServices.GetCity(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseCityName responseCityName;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseCityName = gson.fromJson(paramResponse, ResponseCityName.class);
                        cityList = responseCityName.getCities();
                        for (int i = 0; i < cityList.size(); i++) {
                            CityMenu.getMenu().add(0, 0, i, cityList.get(i).getName());
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

    public void GetJobDetails() {
        showLoading();
        JsonObject object = new JsonObject();
        //object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
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


        Call<ResponseCommon> call = apiServices.GetRecentJob(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseJobList responseJobList;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseJobList = gson.fromJson(paramResponse, ResponseJobList.class);

                        RecentJobAdapter adapter = new RecentJobAdapter(responseJobList.getRecentJob(), context, FragmentSearchJob.this);
                        binding.rvRecentjobs.setAdapter(adapter);


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
