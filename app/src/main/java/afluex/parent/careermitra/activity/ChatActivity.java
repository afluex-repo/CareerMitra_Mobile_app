package afluex.parent.careermitra.activity;



import static afluex.parent.careermitra.app.AppConfig.PAYLOAD_BUNDLE;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.AdapterChat;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ChatActivityBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.chatMessages.ResponseChatMessages;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BaseActivity {



    EditText etComment;
    Bundle bundle;
    SwipeRefreshLayout swipe_refresh;

    ChatActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        swipe_refresh = findViewById(R.id.swipeRefresh);
        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        binding.rvMessages.setLayoutManager(new LinearLayoutManager(context));

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getMessages();
        else showMessage(getResources().getString(R.string.alert_internet));

        swipe_refresh.setOnRefreshListener(() -> {
            swipe_refresh.setRefreshing(false);
            getMessages();
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgPostCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etComment.getText().toString().trim().length() > 0) {
                    SendMessage();
                } else showMessage(getString(R.string.write));
            }
        });
    }



    public void getMessages() {
        showLoading();
        JsonObject object = new JsonObject();

        object.addProperty("userId", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("id", bundle.getString("id"));

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetMessageConversation(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseChatMessages responseMessageList;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);


                        Gson gson = new GsonBuilder().create();
                        responseMessageList = gson.fromJson(paramResponse, ResponseChatMessages.class);
                        AdapterChat adapterMessageList = new AdapterChat(context, responseMessageList.getMessages());
                        binding.rvMessages.setAdapter(adapterMessageList);
                        binding.rvMessages.scrollToPosition(responseMessageList.getMessages().size() - 1);
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

    private void SendMessage() {
        showLoading();
        JsonObject object = new JsonObject();

        object.addProperty("userId", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("id", bundle.getString("id"));
        object.addProperty("message", etComment.getText().toString().trim());

        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.SendMessage(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        etComment.setText("");
                        new Handler().postDelayed(() -> getMessages(), 100);
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
}
