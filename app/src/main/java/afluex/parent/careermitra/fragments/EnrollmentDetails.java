package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.EnrollmentProgramAdapter;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityTrainingViewBinding;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.resopnseEnrollmentProgram.ResponseEnrollment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentDetails extends BaseFragment implements JobDetailsListener {


    Bundle bundle;

    ActivityTrainingViewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      binding=ActivityTrainingViewBinding.inflate(inflater,container,false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvcEnrollment.setLayoutManager(layoutManager);

        GetJobDetails();
        return binding.getRoot();
    }
    public void GetJobDetails() {
        showLoading();

        JsonObject object = new JsonObject();

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





        Call<ResponseCommon> call = apiServices.GetEnrollment(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseEnrollment responseEnrollment;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseEnrollment = gson.fromJson(paramResponse, ResponseEnrollment.class);

                        EnrollmentProgramAdapter adapter = new EnrollmentProgramAdapter(responseEnrollment.getLstProgram(),context,EnrollmentDetails.this);
                        binding.rvcEnrollment.setAdapter(adapter);
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
    public void openJobDetails(String enrollmentProgramId) {
         bundle = new Bundle();
        bundle.putString("Id", enrollmentProgramId);
      //  goToActivity(AboutJobDetails.class, bundle);*/
      applyEnrollment();
    }
    private void applyEnrollment()
    {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("enrollmentProgramId", bundle.getString("Id"));


        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("deviceType", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetApplyEnrollment(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200))
                      //  onBackPressed();
                    showMessage(response.body().getMessage());
                    else showMessage(response.body().getMessage());
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