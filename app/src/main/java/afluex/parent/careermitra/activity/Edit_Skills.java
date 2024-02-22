package afluex.parent.careermitra.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.AdapterSkills;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.CustomActionBarBinding;
import afluex.parent.careermitra.databinding.EditSkillsEndorsementsBinding;
import afluex.parent.careermitra.interfaces.SkillListener;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.skills.ResponseSkills;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Skills extends BaseActivity implements SkillListener {

   EditSkillsEndorsementsBinding binding;

    CustomActionBarBinding toolbar;
    JsonArray jsonArray = new JsonArray();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditSkillsEndorsementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=binding.toolbar;
       toolbar.tvTitle.setText(R.string.update_skills);

        binding.rvSkills.setLayoutManager(new LinearLayoutManager(context));

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getSkills();
        } else showMessage(getResources().getString(R.string.alert_internet));

        toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (jsonArray.size() != 0)
                        updateSkills();
                    else
                        showMessage("Enter Your Skills");
                } else showMessage(getResources().getString(R.string.alert_internet));
            }
        });
    }



    private void updateSkills() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.add("skillIds", jsonArray);
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.updateSkills(bodyParam(object));
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

    private void getSkills() {
        showLoading();
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
                        AdapterSkills adapterSkills = new AdapterSkills(context, skills.getSkills(), Edit_Skills.this);
                        binding.rvSkills.setAdapter(adapterSkills);
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
    public void selectedSkill(String skillId, boolean toadd) {
        if (toadd)
            jsonArray.add(skillId);
        else {
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).getAsString().equalsIgnoreCase(skillId))
                    jsonArray.remove(i);
            }
        }
    }
}