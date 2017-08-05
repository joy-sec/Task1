package net.apkkothon.tourkit.fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.MapsActivity;
import net.apkkothon.tourkit.PlaceDetailsActivity;
import net.apkkothon.tourkit.R;


public class PlaceDetailsFragment extends Fragment {

    private TextView name,details,district;
    private ImageView imageView;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_place_details,container,false);
        //initialize
        name=(TextView)view.findViewById(R.id.name);
        details=(TextView)view.findViewById(R.id.details);
        district=(TextView)view.findViewById(R.id.district);
        imageView=(ImageView)view.findViewById(R.id.imageView);
        button=(Button)view.findViewById(R.id.map);

        ((PlaceDetailsActivity)getActivity()).getSupportActionBar().setTitle(getActivity().getIntent().getStringExtra("name"));


        name.setText(getActivity().getIntent().getStringExtra("name"));
        details.setText(getActivity().getIntent().getStringExtra("details"));
        district.setText(getActivity().getIntent().getStringExtra("district"));

        Picasso.with(getContext()).load(getActivity().getIntent().getStringExtra("image")).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                startActivity(intent);
            }
        });

        return view;
    }
}
