package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.interfaces.JobDetailsListener;
import afluex.parent.careermitra.model.response.resopnseEnrollmentProgram.LstProgram;

import java.util.List;




public class EnrollmentProgramAdapter  extends RecyclerView.Adapter<EnrollmentProgramAdapter.ViewHolder> {

    private List<LstProgram> models;
    private Context context;
    private JobDetailsListener listener;


    public EnrollmentProgramAdapter(List<LstProgram> models, Context context, JobDetailsListener listener) {
        this.models = models;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_enrollment_program, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(models.get(i).getTitle());
        viewHolder.tvDiscription.setText(models.get(i).getDescription());
        viewHolder.tvStartDate.setText(models.get(i).getStartDate());
        viewHolder.tvEndDate.setText(models.get(i).getEndDate());
        Glide.with(context).load(models.get(i).getImage()).into(viewHolder.imageview);
        viewHolder.apply_now.setOnClickListener(v -> listener.openJobDetails(models.get(i).getId()));


        //viewHolder.app.setOnClickListener(v ->listener.openJobDetails(models.get(i).
    }
    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;

        TextView tvDiscription;

        TextView tvStartDate;

        TextView tvEndDate;

        ImageView imageview;

        TextView apply_now;

        public ViewHolder(View view) {
            super(view);

            tvTitle=view.findViewById(R.id.tv_title);
            tvDiscription=view.findViewById(R.id.tv_Discription);
            tvStartDate=view.findViewById(R.id.tv_start_date);
            tvEndDate=view.findViewById(R.id.tv_End_date);
            imageview=view.findViewById(R.id.image_view);
            apply_now=view.findViewById(R.id.apply_now);




        }

    }
    }
