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
import afluex.parent.careermitra.model.response.responseNotification.LstNotification;

import java.util.List;




public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<LstNotification> models;
    private Context context;

    public NotificationAdapter(List<LstNotification> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(models.get(i).getTitle());
        viewHolder.tvMessage.setText(models.get(i).getDescription());
        viewHolder.tv_update_time.setText(models.get(i).getDate());
        viewHolder.tvLink.setText(models.get(i).getLink());
        Glide.with(context).load(models.get(i).getImage()).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;

        TextView tvLink;

        TextView tvMessage;


        TextView tv_update_time;

        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tvTitle=view.findViewById(R.id.tv_tiltle);
            tvLink=view.findViewById(R.id.tv_link);
            tvMessage=view.findViewById(R.id.tv_message);
            tv_update_time=view.findViewById(R.id.tv_update_time);
            imageView=view.findViewById(R.id.img_profile);

        }
    }
}
