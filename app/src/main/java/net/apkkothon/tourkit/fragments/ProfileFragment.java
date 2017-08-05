package net.apkkothon.tourkit.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.LoginActivity;
import net.apkkothon.tourkit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment{

    private ImageView imageView;
    private ImageButton imageButton;
    private TextView name,email,phone,location,login;
    private String strEmail;
    private LinearLayout linearLayout;
    private ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);


        imageView=(ImageView)view.findViewById(R.id.image_profile_frag);
        imageButton=(ImageButton)view.findViewById(R.id.imageButton3);
        name=(TextView)view.findViewById(R.id.fullName);
        location=(TextView)view.findViewById(R.id.location);
        email=(TextView)view.findViewById(R.id.userEmailId) ;
        phone=(TextView)view.findViewById(R.id.mobileNumber);
        scrollView=(ScrollView)view.findViewById(R.id.scrrolView);
        linearLayout=(LinearLayout)view.findViewById(R.id.llayout);
        login=(TextView)view.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        strEmail=sharedPreferences.getString("email","null");

        boolean chk=sharedPreferences.getBoolean("login",false);

        if (chk==true){
            linearLayout.setVisibility(View.GONE);
        }
        else {

            scrollView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }


        parseProfile();

        return view;
    }

    private void parseProfile(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apkkothon.net/tour/profile-json.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray =new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        name.setText(jsonObject.getString("user_name"));
                        location.setText(jsonObject.getString("location"));
                        email.setText(strEmail);
                        phone.setText(jsonObject.getString("mobile"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Something Went wrong ! try later.",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();

                params.put("post_email",strEmail);

                return params;

            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
