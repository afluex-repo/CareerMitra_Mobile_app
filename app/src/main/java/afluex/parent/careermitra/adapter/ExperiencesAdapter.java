package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.model.response.getProfileDetail.ExperiencesItem;

import java.util.List;




public class ExperiencesAdapter extends RecyclerView.Adapter<ExperiencesAdapter.ViewHolder> {


    private List<ExperiencesItem> models;
    private Context context;

    public ExperiencesAdapter(List<ExperiencesItem> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_experiences, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvDesignationNameInCompany.setText(models.get(i).getDesignation());
        viewHolder.tvCompanyName.setText(models.get(i).getCompany());
        viewHolder.tvCompanyJoinDate.setText(models.get(i).getYearFrom() + " - " + models.get(i).getYearTo());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvDesignationNameInCompany;

        TextView tvCompanyName;

        TextView tvCompanyJoinDate;

        public ViewHolder(View view) {
            super(view);
            tvDesignationNameInCompany=view.findViewById(R.id.tv_designation);
            tvCompanyName=view.findViewById(R.id.tv_company_name);
            tvCompanyJoinDate=view.findViewById(R.id.tv_company_join_date);

        }
    }
}
