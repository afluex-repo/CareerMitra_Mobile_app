package afluex.parent.careermitra.activity;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.material.navigation.NavigationView;

import afluex.parent.careermitra.BuildConfig;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.databinding.ActvityDashboardBinding;
import afluex.parent.careermitra.databinding.AppBarContainerBinding;
import afluex.parent.careermitra.databinding.SidemenuLayoutBinding;
import afluex.parent.careermitra.fragments.AboutUs;
import afluex.parent.careermitra.fragments.Dashboard;
import afluex.parent.careermitra.fragments.EnrollmentDetails;
import afluex.parent.careermitra.fragments.Feedback;
import afluex.parent.careermitra.fragments.FragmentAppliedJobs;
import afluex.parent.careermitra.fragments.FragmentSearchJob;
import afluex.parent.careermitra.fragments.RecommendedJob;
import afluex.parent.careermitra.fragments.TraingDetails;




public class ContainerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    
    DrawerMenuItems drawerMenuItems;

    SidemenuLayoutBinding sidemenuLayoutBinding;
   


  
    
    ActvityDashboardBinding binding;
    AppBarContainerBinding toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActvityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sidemenuLayoutBinding=binding.sidemenulayout;
        
        
        toolbar=binding.toolbar;
        

        setSupportActionBar(toolbar.toolbar);


        toolbar.imgNotifications.setOnClickListener(v -> goToActivity(context, Notification.class, null));

        toolbar.rlClickmenu.setOnClickListener(v -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toolbar.imgSearch.setOnClickListener(v -> ReplaceFragment(new FragmentSearchJob(), getResources().getString(R.string.search_job)));

        toolbar.imgSideMenu.setOnClickListener(v -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.navView.setNavigationItemSelectedListener(this);
        View hView = binding.navView.getHeaderView(0);






        if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase("")) {
            sidemenuLayoutBinding.tvUsername.setVisibility(View.GONE);
            sidemenuLayoutBinding.tvLastName.setVisibility(View.GONE);
            sidemenuLayoutBinding.tvLogout.setVisibility(View.GONE);
            sidemenuLayoutBinding.tvChnagePassword.setVisibility(View.GONE);
            toolbar.imgNotifications.setVisibility(View.GONE);

            sidemenuLayoutBinding.tvLogin.setVisibility(View.VISIBLE);

        } else {
            sidemenuLayoutBinding.tvUsername.setVisibility(View.VISIBLE);
            sidemenuLayoutBinding.tvLastName.setVisibility(View.VISIBLE);
            sidemenuLayoutBinding.tvLogout.setVisibility(View.VISIBLE);
            sidemenuLayoutBinding.tvChnagePassword.setVisibility(View.VISIBLE);
            sidemenuLayoutBinding.tvLogin.setVisibility(View.GONE);
            toolbar.imgNotifications.setVisibility(View.VISIBLE);

        }
        ReplaceFragment(new Dashboard(), getResources().getString(R.string.home));
        sidemenuLayoutBinding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                    goToActivity(ContainerActivity.this, Profile.class, null);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentFragment instanceof Dashboard))
                    ReplaceFragment(new Dashboard(), getResources().getString(R.string.home));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvRecommendedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new RecommendedJob(), getResources().getString(R.string.search_job));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvAppliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                    createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                else
                    ReplaceFragment(new FragmentAppliedJobs(), getResources().getString(R.string.applied_jobs));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new FragmentSearchJob(), getResources().getString(R.string.search_job));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvViewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new TraingDetails(), getResources().getString(R.string.traning_crose));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvEnrollmentProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new EnrollmentDetails(), getResources().getString(R.string.enrollment_program));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvChatForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                    createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                else
                    goToActivity(context, RecruitersMessage.class, null);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvChnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, ChangePassword.class, null);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                    createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                else {
                    if (!(currentFragment instanceof Feedback))
                        ReplaceFragment(new Feedback(), getResources().getString(R.string.feedback));
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new AboutUs(), getResources().getString(R.string.about_us));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog(context, Login.class);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }

        });
        sidemenuLayoutBinding.tvSettings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityWithFinish(context, LanguageSeletion.class, null);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        sidemenuLayoutBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityWithFinish(context,Login.class,null);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sidemenuLayoutBinding.tvUsername.setText(PreferencesManager.getInstance(context).getFirstName());
        sidemenuLayoutBinding.tvLastName.setText(PreferencesManager.getInstance(context).getLastName());
        Glide.with(context).load(PreferencesManager.getInstance(context).getImage())
                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher))
                .into(sidemenuLayoutBinding.imgProfile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private Fragment currentFragment;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            if (currentFragment instanceof Dashboard) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getResources().getString(R.string.click_back), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            } else {
                ReplaceFragment(new Dashboard(), getResources().getString(R.string.home));
            }
        } else {
            fm.popBackStack();
        }
    }

    public void ReplaceFragment(Fragment setFragment, String title) {
        new Handler().postDelayed(() -> {
            currentFragment = setFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame, setFragment, title);
            toolbar.tvTitle.setText(title);
            transaction.commitAllowingStateLoss();
        }, 200);
    }



    class DrawerMenuItems {


        public DrawerMenuItems(View hView) {

        }

        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.img_profile:
                    if (!PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                        goToActivity(ContainerActivity.this, Profile.class, null);
                    break;
                case R.id.tv_dashboard:
                    if (!(currentFragment instanceof Dashboard))
                        ReplaceFragment(new Dashboard(), getResources().getString(R.string.home));
                    break;
                case R.id.tv_recommended_job:
                    ReplaceFragment(new RecommendedJob(), getResources().getString(R.string.search_job));
                    break;
                case R.id.tv_applied_jobs:
                    if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                        createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                    else
                        ReplaceFragment(new FragmentAppliedJobs(), getResources().getString(R.string.applied_jobs));
                    break;
                case R.id.tv_search_job:
                    ReplaceFragment(new FragmentSearchJob(), getResources().getString(R.string.search_job));
                    break;
                case R.id.tv_view_training:
                    ReplaceFragment(new TraingDetails(), getResources().getString(R.string.traning_crose));
                    break;
                case R.id.tv_enrollment_program:
                    ReplaceFragment(new EnrollmentDetails(), getResources().getString(R.string.enrollment_program));
                    break;
                case R.id.tv_chat_for_help:
                    if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                        createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                    else
                        goToActivity(context, RecruitersMessage.class, null);
                    break;
                case R.id.tv_chnage_password:
                    //if (!(currentFragment instanceof Settings))
                    //   ReplaceFragment(new Settings(), "Settings");
                    goToActivity(context, ChangePassword.class, null);
                    break;
                case R.id.tv_feedback:
                    if (PreferencesManager.getInstance(context).getUserid().equalsIgnoreCase(""))
                        createLoginDialog(context, getString(R.string.login_first_time), getString(R.string.please_login_to_first_time));
                    else {
                        if (!(currentFragment instanceof Feedback))
                            ReplaceFragment(new Feedback(), getResources().getString(R.string.feedback));
                    }
                    break;
                case R.id.tv_invite_friend:
                    shareApp();
                    break;
                case R.id.tv_about_us:
                    if (!(currentFragment instanceof AboutUs))
                        ReplaceFragment(new AboutUs(), getResources().getString(R.string.about_us));
                    break;
                case R.id.tv_logout:
                    logoutDialog(context, Login.class);
                    break;
                case R.id.tv_settings1:
                    goToActivityWithFinish(context, LanguageSeletion.class, null);
                    break;
                case R.id.tv_login:
                    goToActivityWithFinish(context,Login.class,null);
                    break;
            }
            binding.drawerLayout.closeDrawers();
        }
    }

    void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + afluex.parent.careermitra.BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

}