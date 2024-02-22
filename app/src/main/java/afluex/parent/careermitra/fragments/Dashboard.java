package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.databinding.HomeItemBinding;


public class Dashboard extends BaseFragment {


    HomeItemBinding binding;
    String url_hindi = "https://moradabadsmartcity.org/moradabad_responsive/mindexhi.html";
    String url_english = "https://moradabadsmartcity.org/moradabad_responsive/mindex.html";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding=HomeItemBinding.inflate(inflater,container,false);


        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress < 100) {
//                    showLoading();
//                }
//                if (progress == 100) {
//                    hideLoading();
//                }
//            }
//        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            binding.webView.loadUrl(url_english);
        else
            binding.webView.loadUrl(url_hindi);
        showLoading();
        new Handler().postDelayed(() -> hideLoading(), 1200);
        super.onResume();
    }
}
