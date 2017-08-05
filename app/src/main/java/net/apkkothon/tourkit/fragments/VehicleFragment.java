package net.apkkothon.tourkit.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.AllPlaceActivity;
import net.apkkothon.tourkit.Comments;
import net.apkkothon.tourkit.R;
import net.apkkothon.tourkit.adapters.CommentAdapter;
import net.apkkothon.tourkit.adapters.HomeAdapter;
import net.apkkothon.tourkit.adapters.VehicleAdapter;
import net.apkkothon.tourkit.models.CommentModel;
import net.apkkothon.tourkit.models.VehicleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VehicleFragment extends Fragment {
    private RecyclerView recyclerView;
    private VehicleAdapter adapter;
    private List<VehicleModel> list = new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_vehicle,container,false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_vehicle);
        adapter = new VehicleAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        getdata();
        return view;
    }
    void getdata(){
        progressDialog.show();
        String url = "http://apkkothon.net/tour/vehicle-json.php";
        StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 progressDialog.cancel();
                //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                try {
                    progressDialog.cancel();
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            VehicleModel postModel=new VehicleModel();
                            postModel.setCar_model(jsonObject.getString("car_model"));
                            postModel.setCar_description(jsonObject.getString("car_description"));
                            postModel.setDivision(jsonObject.getString("division"));
                            postModel.setMobile(jsonObject.getString("mobile"));
                            postModel.setImage(jsonObject.getString("image"));
                            postModel.setPrice(jsonObject.getString("price"));
                            list.add(postModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            adapter.notifyItemChanged(i);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("post_division",getActivity().getIntent().getStringExtra("division"));
                return params;
            }

        };

        sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sq);
    }
}
