package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.model.response.getProfileDetail.SkillsItems;

import java.util.List;




public class GetSkilsAdapter extends RecyclerView.Adapter<GetSkilsAdapter.ViewHolder> {


    private List<SkillsItems> models;
    private Context context;

    public GetSkilsAdapter(List<SkillsItems> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skills_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        viewHolder.btnSkills.setText(models.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView btnSkills;

        public ViewHolder(View view) {
            super(view);
            btnSkills=view.findViewById(R.id.btn_skills);



        }
    }

}
