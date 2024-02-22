package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;
import afluex.parent.careermitra.interfaces.OpenChatListener;
import afluex.parent.careermitra.model.response.recruitersListMessage.MessagesItem;

import java.util.List;




public class AdapterMessageList extends RecyclerView.Adapter<AdapterMessageList.ViewHolder> {

    private List<MessagesItem> models;
    private Context context;
    private OpenChatListener listener;

    public AdapterMessageList(List<MessagesItem> models, Context context, OpenChatListener listener) {
        this.models = models;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_message_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_recruiter_name_job_title.setText(models.get(i).getEmployerName() + " (" + models.get(i).getJob() + ")");
        viewHolder.tv_last_msg.setText(models.get(i).getLastMessage());
        viewHolder.tv_msg_time.setText(models.get(i).getDate());
        if (models.get(i).getUnReadCount().equalsIgnoreCase("0"))
            viewHolder.tv_unread_count.setVisibility(View.GONE);
        else viewHolder.tv_unread_count.setVisibility(View.VISIBLE);
        viewHolder.tv_unread_count.setText(models.get(i).getUnReadCount());
        viewHolder.tv_first_letter.setText(String.valueOf(models.get(i).getEmployerName().charAt(0)));

        viewHolder.itemView.setOnClickListener(v -> listener.openChat(models.get(i).getId()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_first_letter;

        TextView tv_recruiter_name_job_title;

        TextView tv_unread_count;

        TextView tv_last_msg;

        TextView tv_msg_time;

        public ViewHolder(View view) {
            super(view);
            tv_first_letter=view.findViewById(R.id.tv_first_letter);
            tv_recruiter_name_job_title=view.findViewById(R.id.tv_recruiter_name_job_title);
            tv_unread_count=view.findViewById(R.id.tv_unread_count);
            tv_last_msg=view.findViewById(R.id.tv_last_msg);
            tv_msg_time=view.findViewById(R.id.tv_msg_time);

        }
    }
}