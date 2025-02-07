package afluex.parent.careermitra.activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.databinding.ChangeLanguageBinding;


public class LanguageSeletion extends BaseActivity {
    ChangeLanguageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChangeLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvSwahili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.getInstance(context).setLanguage("hi");
                setAppLocale(PreferencesManager.getInstance(context).getLanguage());
                PreferencesManager.getInstance(context).setIS_Language_selected(false);
                if (PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
                    goToActivityWithFinish(context, WelcomeIntro.class, null);
                } else goToActivityWithFinish(context, ContainerActivity.class, null);
            }
        });
        binding.tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.getInstance(context).setLanguage("en");
                setAppLocale(PreferencesManager.getInstance(context).getLanguage());
                PreferencesManager.getInstance(context).setIS_Language_selected(false);
                if (PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
                    goToActivityWithFinish(context, WelcomeIntro.class, null);
                } else goToActivityWithFinish(context, ContainerActivity.class, null);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.getInstance(context).setIS_Language_selected(false);
                if (PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
                    goToActivityWithFinish(context, WelcomeIntro.class, null);
                } else goToActivityWithFinish(context, ContainerActivity.class, null);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PreferencesManager.getInstance(context).setIS_Language_selected(false);
        if (!PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
            goToActivityWithFinish(context, WelcomeIntro.class, null);
        } else goToActivityWithFinish(context, ContainerActivity.class, null);
    }
}
