package net.apkkothon.tourkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.adapters.CommentAdapter;
import net.apkkothon.tourkit.models.CommentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comments extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<CommentModel> list = new ArrayList<>();
    Button btn_post;
    EditText edt_comment;
    ProgressDialog progressDialog;
    String post_id,str_email,str_comment,str_time;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        progressDialog=new ProgressDialog(Comments.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_comment);
        commentAdapter = new CommentAdapter(getApplicationContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(commentAdapter);
        initView();
        getComment();
       //set on click
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login=sharedPreferences.getBoolean("login",false);
                if (login==true){
                    str_email=sharedPreferences.getString("email","null");
                    post_id=getIntent().getStringExtra("id");
                    str_time=getTimeStamp();
                    str_comment=edt_comment.getText().toString();
                    addComment(str_email,str_comment,str_time,post_id);
                }else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });


    }
    // Initiate Views
    private void initView(){
        btn_post = (Button) findViewById(R.id.post_comment);
        edt_comment= (EditText) findViewById(R.id.comment);
        sharedPreferences=getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
    }

    void getComment() {
       progressDialog.show();
         post_id=getIntent().getStringExtra("id");

        String url = "http://apkkothon.net/tour/comment-json.php";
        StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // progressDialog.cancel();
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                     progressDialog.cancel();
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            CommentModel postModel=new CommentModel();
                           postModel.setComment(jsonObject.getString("comment"));
                            postModel.setName(jsonObject.getString("user_name"));
                            postModel.setTime(jsonObject.getString("time"));
                            list.add(postModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            commentAdapter.notifyItemChanged(i);
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
                 params.put("post_id",post_id);


                return params;
            }

        };

        sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sq);
    }

    void addComment(final String name,final String comment,final String time,final String id) {
        String url = "http://apkkothon.net/tour/add-comment.php";
        StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Review add", Toast.LENGTH_LONG).show();
                CommentModel postModel=new CommentModel();
                postModel.setComment(comment);
                postModel.setTime(time);
                postModel.setName(name);
                list.add(0,postModel);
                commentAdapter.notifyDataSetChanged();


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
               params.put("post_user_name",name);
                params.put("post_id",id);
                params.put("post_review",comment);
                params.put("post_time",time);
                return params;
            }

        };

        sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sq);
    }
    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
