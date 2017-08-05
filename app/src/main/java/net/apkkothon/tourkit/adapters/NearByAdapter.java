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
import net.apkkothon.tourkit.models.NearByModel;
import net.apkkothon.tourkit.models.Place;

import java.util.ArrayList;
import java.util.List;


public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NearByModel> list;

    public NearByAdapter(Context context,List<NearByModel> list) {

        this.context=context;
        this.list= (ArrayList<NearByModel>) list;
    }

    @Override
    public NearByAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nearby,parent,false);
        return new NearByAdapter.MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(NearByAdapter.MyViewHolder holder, int position) {

        NearByModel model=list.get(position);

        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name,address;
        private ArrayList<NearByModel> list=new ArrayList<>();
        private Context context;


        public MyViewHolder(View itemView,Context context,ArrayList<NearByModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;

            name=(TextView)itemView.findViewById(R.id.name);
            address=(TextView)itemView.findViewById(R.id.address);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();

            NearByModel nearByModel =this.list.get(position);

            //Intent intent=new Intent(this.context, PlaceDetailsActivity.class);


           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent);



        }
    }
}
