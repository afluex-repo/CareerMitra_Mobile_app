package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityFeedbackBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import com.google.gson.JsonObject;





import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends BaseFragment {



    ActivityFeedbackBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding=ActivityFeedbackBinding.inflate(inflater,container,false);


        if (PreferencesManager.getInstance(context).getUserid()!=null) {
           binding.etEmail.setVisibility(View.GONE);
           binding.etName.setVisibility(View.GONE);
           binding.etPhoneNumber.setVisibility(View.GONE);
        }
        else {
            binding.etEmail.setVisibility(View.VISIBLE);
            binding.etName.setVisibility(View.VISIBLE);
            binding.etPhoneNumber.setVisibility(View.VISIBLE);
        }

        binding.sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validateForm())
                        uplodaeFeedback();
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });
        return binding.getRoot();
    }

    public void uplodaeFeedback() {
        showLoading();
        JsonObject object = new JsonObject();

        if (PreferencesManager.getInstance(context).getUserid()!=null) {
            object.addProperty("Email", PreferencesManager.getInstance(context).getEmail());
            object.addProperty("name",PreferencesManager.getInstance(context).getFirstName());
            object.addProperty("mobile",PreferencesManager.getInstance(context).getMobileno());
        }
        else {
            object.addProperty("Email", binding.etEmail.getText().toString().trim());

            object.addProperty("name",binding.etName.getText().toString().trim());
            object.addProperty("mobile",binding.etPhoneNumber.getText().toString().trim());
        }
        object.addProperty("Message", binding.etFeedback.getText().toString().trim());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetFeedback(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                       // onBackPressed();
                        //new Handler().postDelayed(() -> Feedback(), 200);
                       // new Handler().postDelayed(( )->Feedback, 200);
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

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    private boolean validateForm() {

         if (binding.etFeedback.getText().toString().length() < 6) {
            showMessage(getString(R.string.feedback));
            return false;
        }
        return true;
    }
}
