package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.model.response.ResponseAppliedJob.AppliedListJob;

import java.util.List;




public class AdapterAppliedJobs extends RecyclerView.Adapter<AdapterAppliedJobs.ViewHolder> {

    private List<AppliedListJob> models;
    private Context context;

    public AdapterAppliedJobs(List<AppliedListJob> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_applied_jobs, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvCompanyName.setText(models.get(i).getCompany());
        viewHolder.tvAppliedDate.setText("On: " + models.get(i).getDate());
        viewHolder.tvJobName.setText(models.get(i).getTitle());
        viewHolder.tvLongDiscription.setText(models.get(i).getDescription());
        viewHolder.tvSortDiscription.setText(models.get(i).getShortDescription());
        // viewHolder.btnSkills.setText(models.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvJobName;

        TextView tvCompanyName;

        TextView tvSortDiscription;

        TextView tvLongDiscription;

        TextView tvAppliedDate;

        public ViewHolder(View view) {
            super(view);
            tvJobName=view.findViewById(R.id.tv_job_name);
            tvCompanyName=view.findViewById(R.id.tv_company_name);
            tvSortDiscription=view.findViewById(R.id.tv_sort_discription);
            tvLongDiscription=view.findViewById(R.id.tv_long_discription);
            tvAppliedDate=view.findViewById(R.id.tv_applied_date);



        }
    }
}
