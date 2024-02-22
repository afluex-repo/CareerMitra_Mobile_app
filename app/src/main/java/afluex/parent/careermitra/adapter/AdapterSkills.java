package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.interfaces.SkillListener;
import afluex.parent.careermitra.model.response.skills.SkillsItem;

import java.util.List;




public class AdapterSkills extends RecyclerView.Adapter<AdapterSkills.AdapterViewHolder> {
    private Context context;
    private List<SkillsItem> models;
    private SkillListener listener;

    public AdapterSkills(Context context, List<SkillsItem> models, SkillListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_skills, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder holder, int position) {

        if (models.get(position).isSelected())
            holder.cbSkills.setChecked(true);
        else holder.cbSkills.setChecked(false);

        holder.cbSkills.setText(models.get(position).getName());
        holder.cbSkills.setOnClickListener((view) -> {
            if (models.get(position).isSelected()) {
                models.get(position).setSelected(false);
                listener.selectedSkill(models.get(position).getId(), false);
            } else {
                models.get(position).setSelected(true);
                listener.selectedSkill(models.get(position).getId(), true);
            }
            new Handler().postDelayed(() -> notifyDataSetChanged(), 200);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
;
        CheckBox cbSkills;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            cbSkills=itemView.findViewById(R.id.cb_skills);
         ;
        }
    }
}