package net.apkkothon.tourkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class VehicleDetails extends AppCompatActivity {
    TextView txt_model,txt_description,txt_division,txt_price,txt_mobile;
    ImageView car_image,close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        //initialization
        txt_model= (TextView) findViewById(R.id.car_model_details);
        txt_description= (TextView) findViewById(R.id.car_description_details);
        txt_division= (TextView) findViewById(R.id.car_division_details);
        txt_price= (TextView) findViewById(R.id.car_price_details);
        car_image= (ImageView) findViewById(R.id.car_image_details);
        txt_mobile= (TextView) findViewById(R.id.car_mobile_details);
        close= (ImageView) findViewById(R.id.close_activity_vehicle);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //set every thing
        txt_model.setText(getIntent().getStringExtra("model"));
        txt_division.setText(getIntent().getStringExtra("division"));
        txt_description.setText(getIntent().getStringExtra("description"));
        txt_price.setText(getIntent().getStringExtra("price"));
        txt_mobile.setText(getIntent().getStringExtra("mobile"));
        Picasso.with(VehicleDetails.this).load(getIntent().getStringExtra("image")).into(car_image);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
