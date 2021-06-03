package com.kykj.haru2;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;


public class NoteAdd extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    Bundle bundle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteadd);
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "todo-db").build();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();



        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), intent);
        vpPager.setAdapter(adapterViewPager);

//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        indicator.setViewPager(vpPager);
        vpPager.setOffscreenPageLimit(4);




    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;
        private  Intent intent;
        String imgid , image1 ,image2, image3 , year, weather, content;
        Float q1, q2 ,q3;
        int id;
        public MyPagerAdapter(FragmentManager fragmentManager, Intent intent) {
            super(fragmentManager);
            this.intent = intent;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Fragment getItem(int position) {
            id = intent.getIntExtra("id",0);
            year = intent.getStringExtra("year");
            weather = intent.getStringExtra("weather");
            content = intent.getStringExtra("content");
            image1 = intent.getStringExtra("image1");
            image2 = intent.getStringExtra("image2");
            image3 = intent.getStringExtra("image3");
            if(image1 != null && image2 == null && image3 == null){
                image1 = intent.getStringExtra("image1");
                image2 = "";
                image3 = "";
            }else if(image1 != null && image2 != null && image3 == null){
                image1 = intent.getStringExtra("image1");
                image2 = intent.getStringExtra("image2");
                image3 = "";
            }else if(image1 != null && image2 != null && image3 != null){
                image1 = intent.getStringExtra("image1");
                image2 = intent.getStringExtra("image2");
                image3 = intent.getStringExtra("image3");
            }

            q1 = (float)intent.getFloatExtra("q1", 0.0f);
            q2 = (float) intent.getFloatExtra("q2", 0.0f);
            q3 = (float) intent.getFloatExtra("q3", 0.0f);
            switch (position) {
                case 0:

                    String years = "";
                    if(id > 0 ){
                        years = intent.getStringExtra("year");
                        return FirstFragment.newInstance(0, years);
                    }else{
                        Date now = new Date();
                        SimpleDateFormat realYear = new SimpleDateFormat("yyyy.MM.dd");
                        years = realYear.format(now);
                        return FirstFragment.newInstance(0, years);
                    }

                case 1:
                    return SecondFragment.newInstance(1, "Page # 2");
                case 2:
                        return ThirdFragment.newInstance(2, q1,q2,q3);
                case 3:
                    return ForFragment.newInstance("3", id, content, image1,image2,image3);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.w_close) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else{

        }

        return super.onOptionsItemSelected(item);
    }

}
