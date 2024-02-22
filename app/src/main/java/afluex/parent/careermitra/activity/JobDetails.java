package afluex.parent.careermitra.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.databinding.ActivityJobDetailsBinding;
import afluex.parent.careermitra.fragments.AboutCompany;
import com.google.android.material.tabs.TabLayout;




public class JobDetails extends BaseActivity {

    ActivityJobDetailsBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.jobs_search)));
        binding.tabLayout.getTabTextColors();
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.about_company));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(),
                binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    class MyAdapter extends FragmentPagerAdapter {
        Context context;
        int totalTabs;

        public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
            super(fm);
            context = c;
            this.totalTabs = totalTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
//                    AboutJobDetails aboutJobDetails = new AboutJobDetails();
//                    return aboutJobDetails;
                case 1:
                    AboutCompany aboutCompany = new AboutCompany();
                    return aboutCompany;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

}
