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

import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.VehicleDetails;
import net.apkkothon.tourkit.models.VehicleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 8/4/17.
 */

public class VehicleAdapter  extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<VehicleModel> list;

    public VehicleAdapter(Context context,List<VehicleModel> list) {

        this.context=context;
        this.list= (ArrayList<VehicleModel>) list;
    }

    @Override
    public VehicleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicle,parent,false);
        return new VehicleAdapter.MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(VehicleAdapter.MyViewHolder holder, int position) {
        VehicleModel postmodel=list.get(position);
        holder.model.setText(postmodel.getCar_model());
        holder.division.setText(postmodel.getDivision());
        Picasso.with(context).load(postmodel.getImage()).into(holder.carImage);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView model,division,price;
        ImageView carImage;
        ArrayList<VehicleModel> list=new ArrayList<>();
        Context context;


        public MyViewHolder(View itemView,Context context,ArrayList<VehicleModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;

            model= (TextView) itemView.findViewById(R.id.gide_name);
            division= (TextView) itemView.findViewById(R.id.division_vehicle);
            carImage= (ImageView) itemView.findViewById(R.id.gide_image);



            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();

            VehicleModel homeModel=this.list.get(position);

            Intent intent=new Intent(this.context, VehicleDetails.class);

           intent.putExtra("model",homeModel.getCar_model());
            intent.putExtra("image",homeModel.getImage());
            intent.putExtra("description",homeModel.getCar_description());
            intent.putExtra("division",homeModel.getDivision());
            intent.putExtra("price",homeModel.getPrice());
            intent.putExtra("mobile",homeModel.getMobile());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);



        }
    }
}

