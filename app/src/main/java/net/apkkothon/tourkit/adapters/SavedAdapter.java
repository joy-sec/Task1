package net.apkkothon.tourkit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.PlaceDetailsActivity;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.database.MyDBFunctions;
import net.apkkothon.tourkit.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 8/3/17.
 */

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Place> list;

    public SavedAdapter(Context context,List<Place> list) {
        this.context=context;
        this.list= (ArrayList<Place>) list;
    }

    @Override
    public SavedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_saved,parent,false);
        return new SavedAdapter.MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(final SavedAdapter.MyViewHolder holder, final int position) {

        final Place place=list.get(position);

        holder.name.setText(place.getPlaceName());
        holder.place.setText(place.getPlaceDistrict());
        Picasso.with(context).load(place.getPlaceImage()).into(holder.imageView);

        holder.saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDBFunctions dbFunctions=new MyDBFunctions(context);

                dbFunctions.delete_data(place.getPostID());

                //ListsDatabaseList theRemovedItem = list.get(position);

                //Place p=list.get(position);

                list.remove(holder.getAdapterPosition());  // remove the item from list
                notifyItemRemoved(position);

                Toast.makeText(context,"Delete Successfully",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name,place;
        private ImageView imageView,saveIcon;
        private ArrayList<Place> list=new ArrayList<>();
        private Context context;


        public MyViewHolder(View itemView,Context context,ArrayList<Place> list) {
            super(itemView);
            this.context=context;
            this.list=list;

            name=(TextView)itemView.findViewById(R.id.name);
            place=(TextView)itemView.findViewById(R.id.place);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            saveIcon=(ImageView)itemView.findViewById(R.id.saveIcon);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();

            Place homeModel=this.list.get(position);

            Intent intent=new Intent(this.context, PlaceDetailsActivity.class);

            intent.putExtra("name",homeModel.getPlaceName());
            intent.putExtra("details",homeModel.getPlaceDetails());
            intent.putExtra("district",homeModel.getPlaceDistrict());
            intent.putExtra("id",homeModel.getPostID());
            intent.putExtra("image",homeModel.getPlaceImage());
            intent.putExtra("division",homeModel.getPlaceDivision());
            intent.putExtra("lat",homeModel.getPlaceLat());
            intent.putExtra("lon",homeModel.getPlaceLon());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);



        }
    }
}

