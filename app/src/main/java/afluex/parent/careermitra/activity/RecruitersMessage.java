package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.AdapterMessageList;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.RecruitersMsgBinding;
import afluex.parent.careermitra.interfaces.OpenChatListener;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.recruitersListMessage.ResponseMessageList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruitersMessage extends BaseActivity implements OpenChatListener {

    RecruitersMsgBinding binding;

    CustomActionBarBinding toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=RecruitersMsgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=binding.toolbar;

       toolbar.tvTitle.setText(R.string.chat_for_help);
       toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }



    @Override
    protected void onResume() {
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getMessagesList();
        else showMessage(getResources().getString(R.string.alert_internet));
        super.onResume();
    }

    public void getMessagesList() {
        showLoading();
        JsonObject object = new JsonObject();

        object.addProperty("userId", PreferencesManager.getInstance(context).getUserid());

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetMessageList(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseMessageList responseMessageList;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseMessageList = gson.fromJson(paramResponse, ResponseMessageList.class);
                        AdapterMessageList adapterMessageList = new AdapterMessageList(responseMessageList.getMessages(), context, RecruitersMessage.this);
                        binding.rvMessages.setLayoutManager(new LinearLayoutManager(context));
                        binding.rvMessages.setAdapter(adapterMessageList);
                    } else
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
    public void openChat(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        goToActivity(context, ChatActivity.class, bundle);
    }
}
