package net.apkkothon.tourkit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.PlaceDetailsActivity;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.adapters.GuideAdapter;
import net.apkkothon.tourkit.models.GuideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joy on 7/29/17.
 */

public class GuideFragment extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter adapter;
    private List<GuideModel> list=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_place_guide,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        adapter=new GuideAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        parseData();

        return view;
    }

    private void parseData(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apkkothon.net/tour/guide-json.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("gide",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        GuideModel model=new GuideModel();

                        model.setName(jsonObject.getString("name"));
                        model.setlImage(jsonObject.getString("image"));

                        System.out.println(jsonObject.getString("image"));

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

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();

                params.put("post_division",getActivity().getIntent().getStringExtra("division"));

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

}
