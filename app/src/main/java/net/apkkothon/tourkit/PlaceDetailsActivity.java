package net.apkkothon.tourkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.apkkothon.tourkit.fragments.GuideFragment;
import net.apkkothon.tourkit.fragments.NearByFragment;
import net.apkkothon.tourkit.fragments.PlaceDetailsFragment;
import net.apkkothon.tourkit.fragments.VehicleFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TabLayout my_tl;
    private ViewPager my_vp;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getActionBar().setTitle("hello");
        getSupportActionBar().setTitle("hello");

        my_vp = (ViewPager) findViewById(R.id.my_view_pager);
        my_tl = (TabLayout) findViewById(R.id.my_tabs);

        setUpMyViewPager(my_vp);
        my_tl.setupWithViewPager(my_vp);
        //floating action button code
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launching facebook comments activity
               Intent intent = new Intent(PlaceDetailsActivity.this, Comments.class);
                id=getIntent().getStringExtra("id");
                intent.putExtra("id",id);
               startActivity(intent);
            }
        });


    }
    void setUpMyViewPager(ViewPager vp){

        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vpa.addMyFragment(new PlaceDetailsFragment(), "Place");
        vpa.addMyFragment(new GuideFragment(), "Guide");
        vpa.addMyFragment(new VehicleFragment(), "Vehicle");
        vpa.addMyFragment(new NearByFragment(),"Near By");

        vp.setAdapter(vpa);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> my_list = new ArrayList<Fragment>();
        private final List<String> my_titles = new ArrayList<String>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return my_list.get(position);
        }


        @Override
        public int getCount() {
            return my_list.size();
        }

        void addMyFragment(Fragment f, String title){
            my_list.add(f);
            my_titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return my_titles.get(position);
        }


    }
}
