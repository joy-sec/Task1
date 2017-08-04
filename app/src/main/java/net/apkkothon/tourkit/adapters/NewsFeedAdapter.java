package net.apkkothon.tourkit.adapters;

import android.content.Context;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.models.NewsFeedModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pappu on 8/1/17.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<NewsFeedModel> list;

    public NewsFeedAdapter(Context context, List<NewsFeedModel> list) {
        this.context = context;
        this.list = (ArrayList<NewsFeedModel>) list;
    }

    @Override
    public NewsFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new NewsFeedAdapter.MyViewHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(final NewsFeedAdapter.MyViewHolder holder, int position) {

        NewsFeedModel model=list.get(position);

       holder.p_name.setText(model.getProfileName());
        holder.mgs.setText(model.getFeedMgs());
        holder.time.setText(model.getTime());
        //Picasso.with(context).load(model.getProfileImage()).into(holder.p_image);
        if(model.getFeedImage().equals("1")){
            holder.f_image.setVisibility(View.GONE);
        }else {
            Picasso.with(context).load(model.getFeedImage()).into(holder.f_image);
        }
        holder.continue_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView p_name,time,mgs,continue_reading;
        ImageView p_image,f_image;
        ArrayList<NewsFeedModel> list = new ArrayList<>();
        Context context;


        public MyViewHolder(View itemView, Context context, ArrayList<NewsFeedModel> list) {

            super(itemView);
            this.context = context;
            this.list = list;

            p_name= (TextView) itemView.findViewById(R.id.name);
            time= (TextView) itemView.findViewById(R.id.timestamp);
            mgs= (TextView) itemView.findViewById(R.id.txtStatusMsg);
            p_image= (ImageView) itemView.findViewById(R.id.profilePic);
            f_image= (ImageView) itemView.findViewById(R.id.feedImage1);
            continue_reading= (TextView) itemView.findViewById(R.id.continue_reading);



        }
    }
}
