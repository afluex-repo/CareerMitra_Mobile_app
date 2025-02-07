package afluex.parent.careermitra.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.constants.BaseFragment;


public class ChatForHelp extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_training_view, container, false);
        return view;
    }

}
