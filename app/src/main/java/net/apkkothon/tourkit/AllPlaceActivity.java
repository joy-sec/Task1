package net.apkkothon.tourkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.adapters.HomeAdapter;
import net.apkkothon.tourkit.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllPlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayAdapter adapter,unit_adapter;
    private Spinner state,city;
    private RecyclerView recyclerView;
    private Button filter;
    private String str_division,str_district;
    private HomeAdapter homeAdapter;
    private List<Place> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        filter=(Button)findViewById(R.id.filter);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        homeAdapter=new HomeAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(homeAdapter);

        state=(Spinner)findViewById(R.id.spinner);
        city= (Spinner) findViewById(R.id.spinner1);
        state.setOnItemSelectedListener(this);

        adapter=ArrayAdapter.createFromResource(this,R.array.State_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        state.setAdapter(adapter);
        city.setOnItemSelectedListener(this);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               filterData();
            }
        });

        parseData();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        int pos;
        pos=state.getSelectedItemPosition();
        int iden=parent.getId();
        if(iden==R.id.spinner)
        {

            str_division=state.getSelectedItem().toString();
            switch (pos)
            {
                case 0:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Dhaka_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 1:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Chittagong_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 2:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Rajshashi_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 3:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Sylhet_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 4:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Barishal_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 5:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Khulna_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 6:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Rangpur_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                case 7:unit_adapter=ArrayAdapter.createFromResource(
                        this,R.array.Mymensingh_array_filter,android.R.layout.simple_spinner_item
                );
                    break;
                default:
                    break;

            }

            city.setAdapter(unit_adapter);
            unit_adapter.setDropDownViewResource(R.layout.spinner_item);
        }

        str_district=city.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

    private void filterData(){

        recyclerView.removeAllViews();
        list.clear();

        System.out.println(str_district+str_division);

        StringRequest request = new StringRequest(Request.Method.POST, "http://apkkothon.net/tour/filter.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Place place = new Place();

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

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();

                params.put("post_div",str_division);
                params.put("post_dis",str_district);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);

    }
}
