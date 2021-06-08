package com.kykj.haru2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteAdd extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteadd);
        getSupportActionBar().setElevation(0);
        //아래에서 수정된 값을 받기위한 겟 인텐트
        Intent intent = getIntent();


        //슬라이딩 부분은 뷰 페이저를 사용했습니다.
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        //값을 adapterViewPager에 지정
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), intent);
        //어뎁터 설정
        vpPager.setAdapter(adapterViewPager);
        //이전 혹은 다음페이지를 몇개까지 미리 로딩할지 정하는 함수
        vpPager.setOffscreenPageLimit(4);




    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        //페이지는 4개
        private static int NUM_ITEMS = 4;
        private  Intent intent;
        String image1 ,image2, image3 , year, weather, content;
        Float q1, q2 ,q3;
        int id;
        public MyPagerAdapter(FragmentManager fragmentManager, Intent intent) {
            super(fragmentManager);
            this.intent = intent;
        }


        @Override
        public int getCount() {
            //페이지 값 지정 리턴
            return NUM_ITEMS;
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Fragment getItem(int position) {
            //수정을 클릭했을 시 받는 곳
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

            //받은 값들을 newInstance에 보내는 작업
            switch (position) {
                    case 0:
                        //초기화
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
                        //테스트 용도
                        return SecondFragment.newInstance(1, "2프래그먼트");
                    case 2:
                        return ThirdFragment.newInstance(2, q1,q2,q3);
                    case 3:
                        return ForFragment.newInstance("3", id, content, image1,image2,image3);
                    default:
                        return null;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.submenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.w_close) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else{

        }

        return super.onOptionsItemSelected(item);
    }

}
