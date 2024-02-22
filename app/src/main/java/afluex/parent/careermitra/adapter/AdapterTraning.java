package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.interfaces.PdfUrl;
import afluex.parent.careermitra.model.response.responseTraining.LstTMaterial;

import java.util.List;




public class AdapterTraning extends RecyclerView.Adapter<AdapterTraning.ViewHolder> {


    private List<LstTMaterial> models;
    private Context context;
    private PdfUrl listener;

    public AdapterTraning(List<LstTMaterial> models, Context context, PdfUrl listener) {
        this.models = models;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_training, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(models.get(i).getTitle());
        viewHolder.tvShortDescription.setText(models.get(i).getShortDescription());

        viewHolder.tvDescription.setText(models.get(i).getDescription());
        viewHolder.tvLink.setText(models.get(i).getLink());

        //  viewHolder.tvAttachment.setText(models.get(i).getAttachment());

        viewHolder.tvSortDiscription.setText(models.get(i).getShortDescription());
        viewHolder.tvPublishDate.setText("Published On " + models.get(i).getPublishDate());

        Glide.with(context).load(models.get(i).getImage()).into(viewHolder.imageView);
        viewHolder.tvAttachment.setOnClickListener(v -> listener.openPdfView(models.get(i).getAttachment()));


    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView tvTitle;

        TextView tvShortDescription;

        TextView tvDescription;

        TextView tvLink;

        TextView tvRequiredField;

        TextView tvAttachment;

        TextView tvSortDiscription;

        TextView tvPublishDate;


        public ViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.image_view);
            tvTitle=view.findViewById(R.id.tv_title);
            tvShortDescription=view.findViewById(R.id.tv_ShortDescription);
            tvDescription=view.findViewById(R.id.tv_Description);
            tvLink=view.findViewById(R.id.tv_Link);
            tvRequiredField=view.findViewById(R.id.tv_required_field);
            tvAttachment=view.findViewById(R.id.tv_Attachment);
            tvSortDiscription=view.findViewById(R.id.tv_sort_discription);
            tvPublishDate=view.findViewById(R.id.tv_PublishDate);



        }
    }
}
