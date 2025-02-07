package afluex.parent.careermitra.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.model.response.chatMessages.MessagesItem;
import java.util.List;


public class AdapterChat extends RecyclerView.Adapter<AdapterChat.CityViewHolder> {

    Context context;
    List<MessagesItem> models;

    public AdapterChat(Context context, List<MessagesItem> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_chat, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        if (models.get(position).getSenderId().equalsIgnoreCase(PreferencesManager.getInstance(context).getUserid())) {
            holder.lvReceive.setVisibility(View.GONE);
            holder.lvSend.setVisibility(View.VISIBLE);
            holder.tvSend.setText(models.get(position).getMessage());
            holder.tvSendTime.setText(Utils.changeDateFormat(models.get(position).getDate()));
        } else {
            holder.lvSend.setVisibility(View.GONE);
            holder.lvReceive.setVisibility(View.VISIBLE);
            holder.tvReceive.setText(models.get(position).getMessage());
            holder.tvRecTime.setText(Utils.changeDateFormat(models.get(position).getDate()));
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        TextView tvReceive;

        TextView tvRecTime;

        LinearLayout lvReceive;

        TextView tvSend;

        TextView tvSendTime;

        LinearLayout lvSend;

        ImageView imgRec;

        ImageView imgSend;



        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceive=itemView.findViewById(R.id.tv_receive);
            tvRecTime=itemView.findViewById(R.id.tv_rec_time);
            lvReceive=itemView.findViewById(R.id.lv_receive);
            tvSend=itemView.findViewById(R.id.tv_send);
            tvSendTime=itemView.findViewById(R.id.tv_send_time);
            lvSend=itemView.findViewById(R.id.lv_send);
            imgRec=itemView.findViewById(R.id.img_rec);
            imgSend=itemView.findViewById(R.id.img_send);


        }
    }


}