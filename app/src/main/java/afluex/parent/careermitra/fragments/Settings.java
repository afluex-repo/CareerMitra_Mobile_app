package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.constants.BaseFragment;
import afluex.parent.careermitra.databinding.ActivitySettingsBinding;


public class Settings extends BaseFragment {

    ActivitySettingsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
binding=ActivitySettingsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


}
