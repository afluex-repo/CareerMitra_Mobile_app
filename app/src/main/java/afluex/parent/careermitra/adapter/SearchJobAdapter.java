package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.model.responseJobList.ListJobitem;

import java.util.List;




public class SearchJobAdapter extends RecyclerView.Adapter<SearchJobAdapter.ViewHolder> {



    private List<ListJobitem> models;
    private Context context;

    public SearchJobAdapter(List<ListJobitem> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_job_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


       // viewHolder.btnSkills.setText(models.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvJobName;

        TextView tvCompanyName;

        TextView tvRequiredExprience;

        TextView tvJobLocation;

        TextView tvRequiredField;

        TextView tvViewed;

        public ViewHolder(View view) {
            super(view);
            tvJobName=view.findViewById(R.id.tv_job_name);
            tvCompanyName=view.findViewById(R.id.tv_company_name);
            tvRequiredExprience=view.findViewById(R.id.tv_required_exprience);
            tvJobLocation=view.findViewById(R.id.tv_job_location);
            tvRequiredField=view.findViewById(R.id.tv_required_field);
            tvViewed=view.findViewById(R.id.tv_viewed);



        }
    }
}
