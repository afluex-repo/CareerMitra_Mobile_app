package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.model.response.getProfileDetail.EducationsItem;

import java.util.List;




public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {
    private List<EducationsItem> models;
    private Context context;

    public EducationAdapter(List<EducationsItem> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_education, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvUniversityName.setText(models.get(i).getCollege());
        viewHolder.tvHighestEducation.setText(models.get(i).getCourse());
        viewHolder.tv_yop.setText(models.get(i).getYearOfPassing());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvUniversityName;

        TextView tvHighestEducation;

        TextView tv_yop;

        public ViewHolder(View view) {
            super(view);
            tvUniversityName=view.findViewById(R.id.tv_university_name);
            tvHighestEducation=view.findViewById(R.id.tv_highest_education);
            tv_yop=view.findViewById(R.id.tv_yop);

        }
    }
}