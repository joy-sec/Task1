package net.apkkothon.tourkit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.models.CommentModel;

import java.util.ArrayList;
import java.util.List;



public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CommentModel> list;
    public CommentAdapter(Context context,List<CommentModel> list) {

        this.context=context;
        this.list= (ArrayList<CommentModel>) list;
    }
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_lists,parent,false);
        return new CommentAdapter.MyViewHolder(view,context,list);
    }
    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {

       CommentModel model=list.get(position);

       holder.name.setText(model.getName());
        holder.comment.setText(model.getComment());
        holder.time.setText(model.getTime());
       // Picasso.with(context).load(model.getImage()).into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,comment,time;
        private ImageView imageView;
        private ArrayList<CommentModel> list=new ArrayList<>();
        private Context context;
        public MyViewHolder(View itemView,Context context,ArrayList<CommentModel> list) {

            super(itemView);
            this.context=context;
            this.list=list;

          name= (TextView) itemView.findViewById(R.id.name_comment_list);
           comment= (TextView) itemView.findViewById(R.id.comment_comment_list);
            time= (TextView) itemView.findViewById(R.id.time_comment_list);

            //itemView.setOnClickListener(this);

        }
    }

}
