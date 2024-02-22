package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.RecentJobAdapter;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActvityRecommendedJobBinding;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.responseRecentJob.ResponseJobList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedJob extends BaseFragment implements JobDetailsListener {
   ActvityRecommendedJobBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=ActvityRecommendedJobBinding.inflate(inflater,container,false);


        binding.recJobRecommandedjob.setLayoutManager(new LinearLayoutManager(context));


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            GetJobDetails();

        } else showMessage(getResources().getString(R.string.alert_internet));

        return binding.getRoot();
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

                        RecentJobAdapter adapter = new RecentJobAdapter(responseJobList.getRecentJob(), context, RecommendedJob.this);
                        binding.recJobRecommandedjob.setAdapter(adapter);


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
}
