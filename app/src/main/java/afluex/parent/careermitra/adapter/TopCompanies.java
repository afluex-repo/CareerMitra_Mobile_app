package afluex.parent.careermitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import afluex.parent.careermitra.R;

import java.util.ArrayList;

public class TopCompanies extends RecyclerView.Adapter {

    ArrayList personNames;
    ArrayList personImages;
    Context context;

    public TopCompanies(ArrayList personNames, ArrayList personImages, Context context) {
        this.personNames = personNames;
        this.personImages = personImages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_companies_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder) holder;

         myViewHolder.tv_company_name.setText((String) personNames.get(position));
       // myViewHolder.img_company.setImageResource((Integer) personImages.get(position));

        // holder.image.setImageResource(personImages.get(position));
        // implement setOnClickListener event on item view.

    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_company_name,tv_no_of_openings,tv_company_type;
        ImageView img_company;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_company_name=itemView.findViewById(R.id.tv_company_name);
            tv_no_of_openings=itemView.findViewById(R.id.tv_no_of_openings);
            img_company=itemView.findViewById(R.id.img_company);
        }
    }
}
