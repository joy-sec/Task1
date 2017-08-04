package net.apkkothon.tourkit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.PlaceDetailsActivity;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.models.GuideModel;
import net.apkkothon.tourkit.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 7/30/17.
 */

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GuideModel> list;

    public GuideAdapter(Context context,List<GuideModel> list) {

        this.context=context;
        this.list= (ArrayList<GuideModel>) list;
    }

    @Override
    public GuideAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_guide,parent,false);
        return new GuideAdapter.MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(GuideAdapter.MyViewHolder holder, int position) {

        GuideModel model=list.get(position);

        holder.name.setText(model.getName());
        Picasso.with(context).load(model.getImage()).into(holder.gideImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView name;
         ImageView gideImage;
         ArrayList<GuideModel> list=new ArrayList<>();
        Context context;


        public MyViewHolder(View itemView,Context context,ArrayList<GuideModel> list) {

            super(itemView);
            this.context=context;
            this.list=list;

            name=(TextView)itemView.findViewById(R.id.gide_name);
            gideImage=(ImageView)itemView.findViewById(R.id.gide_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();

            GuideModel model=this.list.get(position);

           /* Intent intent=new Intent(this.context, PlaceDetailsActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/

        }
    }
}
