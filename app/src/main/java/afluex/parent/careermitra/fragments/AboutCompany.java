package afluex.parent.careermitra.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.constants.BaseFragment;

public class AboutCompany extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_company, container, false);
    }
}
