package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.model.response.responseFilters.DepartmentsItem;

import java.util.List;




public class AdapterDepartments extends RecyclerView.Adapter<AdapterDepartments.ViewHolder> {

    private List<DepartmentsItem> models;
    private Context context;

    public AdapterDepartments(List<DepartmentsItem> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_departments, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if (PreferencesManager.getInstance(context).getLanguage().equalsIgnoreCase("en"))
            viewHolder.tv_dept_name.setText(models.get(i).getName());
        else  viewHolder.tv_dept_name.setText(models.get(i).getNameH());

//        viewHolder.itemView.setOnClickListener(v -> listener.openJobDetails(models.get(i).getId()));

        AdapterDeptCategories categories = new AdapterDeptCategories(models.get(i).getCategories(), context);
        viewHolder.rv_depat_cate.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.rv_depat_cate.setAdapter(categories);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_dept_name;

        RecyclerView rv_depat_cate;

        public ViewHolder(View view) {
            super(view);
            tv_dept_name=view.findViewById(R.id.tv_dept_name);
            rv_depat_cate=view.findViewById(R.id.rv_depat_cate);


        }
    }
}