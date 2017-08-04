package net.apkkothon.tourkit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.AllPlaceActivity;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.adapters.HomeAdapter;
import net.apkkothon.tourkit.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 7/26/17.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Place> list=new ArrayList<>();
    private TextView tvSeeAll;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home,container,false);

        tvSeeAll=(TextView)view.findViewById(R.id.text2);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        homeAdapter=new HomeAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(homeAdapter);

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AllPlaceActivity.class));

            }
        });

        parseData();

        return view;
    }

    private void parseData(){

        recyclerView.removeAllViews();
        list.clear();

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest("http://apkkothon.net/tour/all-place-json.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {
                        JSONObject jsonObject= (JSONObject) response.get(i);

                        Place place=new Place();

                        place.setPlaceName(jsonObject.getString("name"));
                        place.setPlaceDetails(jsonObject.getString("details"));
                        place.setPlaceDistrict(jsonObject.getString("district"));
                        place.setPlaceImage(jsonObject.getString("url"));
                        place.setPostID(jsonObject.getString("id"));
                        place.setPlaceDivision(jsonObject.getString("division"));
                        place.setPlaceLat(jsonObject.getString("lat"));
                        place.setPlaceLon(jsonObject.getString("lon"));

                        list.add(place);

                        homeAdapter.notifyItemChanged(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);

    }
}
