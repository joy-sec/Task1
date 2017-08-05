package net.apkkothon.tourkit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.adapters.HomeAdapter;
import net.apkkothon.tourkit.adapters.SavedAdapter;
import net.apkkothon.tourkit.database.MyDBFunctions;
import net.apkkothon.tourkit.models.Place;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private SavedAdapter adapter;
    private List<Place> list=new ArrayList<>();

    public SavedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        list=new MyDBFunctions(getContext()).retriveData();
        adapter=new SavedAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.removeAllViews();
        list=new MyDBFunctions(getContext()).retriveData();
        adapter=new SavedAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }

}

