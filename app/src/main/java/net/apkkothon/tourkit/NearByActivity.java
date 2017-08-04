package net.apkkothon.tourkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.adapters.NearByAdapter;
import net.apkkothon.tourkit.models.NearByModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NearByActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String lat,lon,cat;
    private NearByAdapter adapter;
    private List<NearByModel> list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        adapter=new NearByAdapter(NearByActivity.this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(NearByActivity.this));
        recyclerView.setAdapter(adapter);

        lat=getIntent().getStringExtra("lat");
        lon=getIntent().getStringExtra("lon");
        cat=getIntent().getStringExtra("cat");


        parseData();


    }

    private void parseData(){

        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius=5000&type="+cat+"&key=AIzaSyD793wVK26NKM5lpJ_8OLgBiXNW5R_rOOA";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("results");

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        NearByModel model=new NearByModel();

                        model.setName(jsonObject.getString("name"));
                        model.setAddress(jsonObject.getString("vicinity"));


                        list.add(model);

                        adapter.notifyDataSetChanged();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NearByActivity.this, "Error",Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }

}
