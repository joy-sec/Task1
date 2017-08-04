package net.apkkothon.tourkit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.apkkothon.tourkit.NearByActivity;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.adapters.HomeAdapter;

/**
 * Created by joy on 7/31/17.
 */

public class NearByFragment extends Fragment {

    TextView hotels,hospitals,atm,police,pharmacy,restaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_nearby,container,false);

        hotels=(TextView)view.findViewById(R.id.hotel);
        hospitals=(TextView)view.findViewById(R.id.hospital);
        atm=(TextView)view.findViewById(R.id.atm);
        police=(TextView)view.findViewById(R.id.police);
        pharmacy=(TextView)view.findViewById(R.id.pharmacy);
        restaurant=(TextView)view.findViewById(R.id.restaurant);


        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","hotel");
                startActivity(intent);

            }
        });

        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","hospital");
                startActivity(intent);
            }
        });

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","atm");
                startActivity(intent);
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","police");
                startActivity(intent);
            }
        });

        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","pharmacy");
                startActivity(intent);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NearByActivity.class);
                intent.putExtra("lat",getActivity().getIntent().getStringExtra("lat"));
                intent.putExtra("lon",getActivity().getIntent().getStringExtra("lon"));
                intent.putExtra("cat","restaurant");
                startActivity(intent);
            }
        });


        return view;
    }
}
