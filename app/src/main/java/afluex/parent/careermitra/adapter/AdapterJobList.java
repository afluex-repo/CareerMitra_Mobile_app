package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.SearchJob.ListJob;

import java.util.List;




public class AdapterJobList extends RecyclerView.Adapter<AdapterJobList.ViewHolder> {

    private List<ListJob> models;
    private Context context;
    private JobDetailsListener listener;

    public AdapterJobList(List<ListJob> models, Context context, JobDetailsListener listener) {
        this.models = models;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_job_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvJobName.setText(models.get(i).getTitle());
        if (TextUtils.isEmpty(models.get(i).getCompanyName()) )
        {
            viewHolder.tvCompanyName.setVisibility(View.GONE);
         //


        }

        viewHolder.tvJobLocation.setVisibility(View.GONE);
        viewHolder.tvCompanyName.setText(models.get(i).getCompanyName());
        viewHolder.tvJobLocation.setText(models.get(i).getCity());
        viewHolder.tvRequiredExprience.setText("Experience("+models.get(i).getExperienceMin() + " - " + models.get(i).getExperienceMax()+")");
        viewHolder.tvRequiredField.setText(models.get(i).getCategory());
        viewHolder.tvFullTime.setText(models.get(i).getSalaryMin() + " - " + models.get(i).getSalaryMax() + " , " + models.get(i).getJobType());
        viewHolder.tvSortDiscription.setText(models.get(i).getShortDescription());

        viewHolder.itemView.setOnClickListener(v -> listener.openJobDetails(models.get(i).getId()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvFullTime;

        TextView tvSortDiscription;

        TextView tvJobName;

        TextView tvCompanyName;

        TextView tvRequiredExprience;

        TextView tvJobLocation;

        TextView tvRequiredField;

        TextView tvViewed;

        public ViewHolder(View view) {
            super(view);
            tvFullTime=view.findViewById(R.id.tv_full_time);
            tvSortDiscription=view.findViewById(R.id.tv_sort_discription);
            tvJobName=view.findViewById(R.id.tv_job_name);
            tvCompanyName=view.findViewById(R.id.tv_company_name);
            tvRequiredExprience=view.findViewById(R.id.tv_required_exprience);
            tvJobLocation=view.findViewById(R.id.tv_job_location);
            tvRequiredField=view.findViewById(R.id.tv_required_field);
            tvViewed=view.findViewById(R.id.tv_viewed);


        }
    }
}