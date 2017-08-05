package net.apkkothon.tourkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GuideProfile extends AppCompatActivity {
    TextView txt_model,txt_description,txt_division,txt_price,txt_mobile,txt_language,txt_license;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_profile);
        close= (ImageView) findViewById(R.id.close_activity);
        txt_model= (TextView) findViewById(R.id.pro_guide_name);
        txt_description= (TextView) findViewById(R.id.description_details);
        txt_division= (TextView) findViewById(R.id.division);
        txt_price= (TextView) findViewById(R.id.price_details);
        txt_mobile= (TextView) findViewById(R.id.mobile_details);
        txt_language=(TextView)findViewById(R.id.language);
        txt_license= (TextView) findViewById(R.id.license);
        txt_model.setText(getIntent().getStringExtra("name"));
        txt_division.setText(getIntent().getStringExtra("division"));
        txt_description.setText(getIntent().getStringExtra("description"));
        txt_price.setText(getIntent().getStringExtra("price"));
        txt_mobile.setText(getIntent().getStringExtra("mobile"));
        txt_language.setText(getIntent().getStringExtra("language"));
        txt_license.setText(getIntent().getStringExtra("license"));
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
