package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.LoggerUtil;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityAboutUsBinding;
import afluex.parent.careermitra.model.response.ResponseAboutUs;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUs extends BaseFragment {

  

    ActivityAboutUsBinding binding;
   

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     binding=ActivityAboutUsBinding.inflate(inflater,container,false);
        


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAboutUS();
        } else showMessage(getResources().getString(R.string.alert_internet));

        return binding.getRoot();
    }

    private void getAboutUS() {
        showLoading();
        JsonObject object = new JsonObject();
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            object.addProperty("language", "1");
        else object.addProperty("language", "2");
        object.addProperty("pageName", "AboutUs");


        Call<ResponseCommon> call = apiServices.getAboutUs(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseAboutUs responseAboutUs;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseAboutUs = gson.fromJson(paramResponse, ResponseAboutUs.class);
                        LoggerUtil.logItem(responseAboutUs);

                        binding.tvAboutUs.setText(responseAboutUs.getText());

                        // showMessage(response.body().getMessage());

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

