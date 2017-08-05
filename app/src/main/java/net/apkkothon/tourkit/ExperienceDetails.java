package net.apkkothon.tourkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExperienceDetails extends AppCompatActivity {
    ImageView profileImage,feedImage,close;
    TextView name,time,mgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_details);
        //initialization
        profileImage= (ImageView) findViewById(R.id.profilePic_exp_details);
        feedImage= (ImageView) findViewById(R.id.feedImage_exp_details);
        close= (ImageView) findViewById(R.id.close_activity);
        name= (TextView) findViewById(R.id.name_exp_details);
        time= (TextView) findViewById(R.id.timestamp_exp_details);
        mgs= (TextView) findViewById(R.id.txtStatusMsg_exp_details);
        //set value
        //Picasso.with(ExperienceDetails.this).load(getIntent().getStringExtra("profile_image")).into(profileImage);
        Picasso.with(ExperienceDetails.this).load(getIntent().getStringExtra("feed_image")).into(feedImage);
        name.setText(getIntent().getStringExtra("name"));
        time.setText(getIntent().getStringExtra("time"));
        mgs.setText(getIntent().getStringExtra("mgs"));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
