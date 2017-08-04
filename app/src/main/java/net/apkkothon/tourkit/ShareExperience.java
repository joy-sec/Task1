package net.apkkothon.tourkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apkkothon.tourkit.adapters.NewsFeedAdapter;
import net.apkkothon.tourkit.models.NewsFeedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShareExperience extends AppCompatActivity {
    CardView card1,card2;
    RecyclerView recyclerView;
    NewsFeedAdapter newsFeedAdapter;
    ArrayList<NewsFeedModel> list=new ArrayList<>();
    ImageView profile_image1,profile_image2,chhoseImageView;
    EditText edt_mgs;
    Button btn_choose_img,btn_post,btn_cancle;
    private int PICK_IMAGE_REQUEST=1;
    private Uri filePath;
    private Bitmap bitmap;
    String str_mgs,str_image,str_email,str_time;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_experience);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog=new ProgressDialog(ShareExperience.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");
        //inisialization
        sharedPreferences=getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
        card1= (CardView) findViewById(R.id.card1);
        card2= (CardView) findViewById(R.id.card2);
        profile_image2= (ImageView) findViewById(R.id.imageButton);
        profile_image1= (ImageView) findViewById(R.id.profile_image);
        edt_mgs= (EditText) findViewById(R.id.editmgs);
        chhoseImageView= (ImageView)findViewById(R.id.choose_image_view);
        btn_choose_img= (Button) findViewById(R.id.btn_choose_image);
        btn_post= (Button) findViewById(R.id.post);
        btn_cancle= (Button) findViewById(R.id.cancel);

        //on clicl
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean login=sharedPreferences.getBoolean("login",false);
                if (login==true){
                    card1.setVisibility(view.GONE);
                    card2.setVisibility(view.VISIBLE);
                }
                else {
                    new CustomToast().Show_Toast(ShareExperience.this, view,
                            "You have to login first");
                }

            }
        });
        btn_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap!=null){
                    str_image=getStringImage(bitmap);
                }else str_image="1";
                System.out.println(str_image);
                str_mgs=edt_mgs.getText().toString();
                str_email=sharedPreferences.getString("email","null");
                str_time=getTimeStamp();
                addFeed(str_image,str_mgs,str_time,str_email);
                card2.setVisibility(View.GONE);
                card1.setVisibility(View.VISIBLE);

            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card2.setVisibility(View.GONE);
                card1.setVisibility(View.VISIBLE);
            }
        });
        //recycler view
        recyclerView= (RecyclerView) findViewById(R.id.feedRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        newsFeedAdapter=new NewsFeedAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(newsFeedAdapter);
        getData();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void getData(){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apkkothon.net/tour/experience-json.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  System.out.println(response);
                        recyclerView.removeAllViews();
                        list.clear();
                         progressDialog.cancel();


                        try {
                            JSONArray jsonArray=new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    NewsFeedModel postModel = new NewsFeedModel();

                                    postModel.setProfileName(jsonObject.getString("profile_name"));
                                    postModel.setProfileImage(jsonObject.getString("profile_image"));
                                    postModel.setTime(jsonObject.getString("time"));
                                    postModel.setFeedImage(jsonObject.getString("feed_image"));
                                    postModel.setFeedMgs(jsonObject.getString("feed_mgs"));

                                    list.add(postModel);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } finally {
                                    newsFeedAdapter.notifyItemChanged(i);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Something Went Wrong !",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("post_sender_email", email);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    void addFeed(final String image,final String mgs,final String time,final String email){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apkkothon.net/tour/add-experience.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // System.out.println(response);
                        progressDialog.cancel();
                        NewsFeedModel postModel=new NewsFeedModel();
                        postModel.setTime(time);
                        postModel.setFeedMgs(mgs);

                        try {
                            JSONArray jsonArray=new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    postModel.setProfileName(jsonObject.getString("user_name"));
                                    postModel.setProfileImage(jsonObject.getString("image"));
                                    postModel.setFeedImage(jsonObject.getString("f_image"));
                                    list.add(0,postModel);
                                    newsFeedAdapter.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } finally {


                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Something Went Wrong !",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("post_email", email);
                params.put("post_time",time);
                params.put("post_image",image);
                params.put("post_mgs",mgs);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                chhoseImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

}
