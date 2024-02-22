package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.model.response.responseFilters.QualificationItem;

import java.util.List;




public class AdapterQualification extends RecyclerView.Adapter<AdapterQualification.ViewHolder> {

    private List<QualificationItem> models;
    private Context context;

    public AdapterQualification(List<QualificationItem> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_dept_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (models.get(i).isSelected())
            viewHolder.tv_dept_name.setChecked(true);
        else viewHolder.tv_dept_name.setChecked(false);
        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
        viewHolder.tv_dept_name.setText(models.get(i).getName());
        else  viewHolder.tv_dept_name.setText(models.get(i).getNameH());
//        viewHolder.itemView.setOnClickListener(v -> listener.openJobDetails(models.get(i).getId()));

        viewHolder.tv_dept_name.setOnClickListener(v -> {
            if (models.get(i).isSelected())
                models.get(i).setSelected(false);
            else models.get(i).setSelected(true);
            new Handler().postDelayed(() -> notifyDataSetChanged(), 200);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CheckBox tv_dept_name;

        public ViewHolder(View view) {
            super(view);
            tv_dept_name=view.findViewById(R.id.tv_dept_cat_name);

        }
    }
}
