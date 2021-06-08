package com.kykj.haru2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;


public class View_memo extends AppCompatActivity {
    private AppDatabase db;

    TextView view_year ,view_content;
    ImageView view_weather;
    RatingBar start1, start2 , start3;
    Bitmap[] mSelectedPhotoBmp;
    String imgid;

    long backKeyPressedTime = 0;
    Toast toast;
    LayoutInflater inflater;
    String[] imgCount;

    private int id;
    private String year, weather, image1, image2, image3, content;
    private float q1, q2, q3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_main);
        getSupportActionBar().setElevation(0);
        view_year = (TextView)findViewById(R.id.view_year);
        view_content = (TextView)findViewById(R.id.view_content);
        view_weather = (ImageView) findViewById(R.id.view_weather);
        start1 = (RatingBar)findViewById(R.id.view_q1);
        start2 = (RatingBar)findViewById(R.id.view_q2);
        start3 = (RatingBar)findViewById(R.id.view_q3);
        db = Room.databaseBuilder(this,AppDatabase.class, "todo-db").allowMainThreadQueries().build();
        try {
            if(fetch()) {
                // fetch를 했을 때 db 또는 ForFragment에서 값 불러오기가 성공하였을 경우에만 draw
                draw();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    private void drawWeather(String weather) {
        // view_weather에 받은 문자열에 따라 아이콘 등록
        if (weather.equals("화창했어요.")) {
            view_weather.setImageResource(R.drawable.w_s_son);
        } else if (weather.equals("비가 왔어요.")) {
            view_weather.setImageResource(R.drawable.w_s_rain);
        } else if (weather.equals("흐렸어요.")) {
            view_weather.setImageResource(R.drawable.w_s_cloud);
        } else if (weather.equals("눈이 왔어요.")) {
            view_weather.setImageResource(R.drawable.w_s_snow);
        } else if (weather.equals("번개가 쳤어요.")) {
            view_weather.setImageResource(R.drawable.w_s_bunge);
        }
    }
    private void drawRating(float q1, float q2, float q3) {
        // rating 바 업데이트
        start1.setRating(q1);
        start2.setRating(q2);
        start3.setRating(q3);
    }
    private void drawImages(String image1, String image2, String image3) {
        // 이미지 슬라이드 그리기
        int currentImageLength = 0;
        try {
            if(image1 != null && image2 != null && image3 != null) {
                // 이미지가 3개인 경우
                currentImageLength = 3;
                Uri uri1 = Uri.parse(image1);
                Uri uri2 = Uri.parse(image2);
                Uri uri3 = Uri.parse(image3);
                //이미지의 CONTENT://주소를 스트림 주소로 변경
                InputStream input = this.getContentResolver().openInputStream(uri1);
                InputStream input2 = this.getContentResolver().openInputStream(uri2);
                InputStream input3 = this.getContentResolver().openInputStream(uri3);
                //비트맵으로 DECODE
                mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2), BitmapFactory.decodeStream(input3)};
            } else if(image1 != null && image2 != null) {
                // 이미지가 2개인 경우
                currentImageLength = 2;
                Uri uri1 = Uri.parse(image1);
                Uri uri2 = Uri.parse(image2);
                InputStream input = this.getContentResolver().openInputStream(uri1);
                InputStream input2 = this.getContentResolver().openInputStream(uri2);
                mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2)};
            } else if(image1 != null) {
                // 이미지가 1개인 경우
                currentImageLength = 1;
                Uri uri1 = Uri.parse(image1);
                //이미지의 CONTENT://주소를 스트림 주소로 변경
                InputStream input = this.getContentResolver().openInputStream(uri1);
                //비트맵으로 DECODE
                mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input)};
            } else {
                // 이미지가 없으므로 이미지 슬라이더 뷰를 숨기고 종료
                LinearLayout img_slider_view = (LinearLayout)findViewById(R.id.img_slider_view);
                img_slider_view.setVisibility(View.GONE);
                return;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        final int lastImageLength = currentImageLength;

        //이미지 슬라이더를 위한 페이지 어뎁터
        PagerAdapter pagerAdapter = new PagerAdapter() {
            //갯수를 알기 위해 배열에 담는다.
            final int imageLength = lastImageLength;
            @Override
            public int getCount() {
                /*
                if (imgid.equals("one_image")) {
                    imgCount = new String[]{todo.getImgname1()};
                } else if (imgid.equals("two_image")) {
                    imgCount = new String[]{todo.getImgname1(), todo.getImgname2()};
                } else if (imgid.equals("three_image")) {
                    imgCount = new String[]{todo.getImgname1(), todo.getImgname2(), todo.getImgname3()};
                }
                return imgCount.length;*/
                return imageLength;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == ((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.slider_image, container, false);
                ImageView imageView = view.findViewById(R.id.slider_image);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 새로 추가/수정 일때만 있엇던 것
                imageView.setImageBitmap(mSelectedPhotoBmp[position]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.invalidate();
            }
        };
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        CircleIndicator indicator;
        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }
    private List<Todo> getList(int id) {
        List<Todo> list = (List<Todo>) db.todoDao().Selectid(id);
        return list;
    }

    private boolean fetch() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("View_id", 0);
        // id(getIntExtra)가 0보다 크면 MainActivity에서 넘어온 것이고 아니면 ForFragment에서 넘어온 것이다.
        if(id > 0) {
            this.id = id;
            List<Todo> list = getList(id);
            Todo todo = list.get(0);
            year = todo.getYear();
            weather = todo.getWeather();
            q1 = todo.getStar_one();
            q2 = todo.getStar_two();
            q3 = todo.getStar_three();
            image1 = todo.getImgname1();
            image2 = todo.getImgname2();
            image3 = todo.getImgname3();
            content = todo.getContent();
        } else {
            this.id = intent.getIntExtra("update_id", 0);
            year = intent.getStringExtra("year");
            weather = intent.getStringExtra("weather");
            q1 = (float) intent.getFloatExtra("startTotal", 0.0f);
            q2 = (float) intent.getFloatExtra("startTotal2", 0.0f);
            q3 = (float) intent.getFloatExtra("startTotal3", 0.0f);
            image1 = intent.getStringExtra("setImgname1");
            image2 = intent.getStringExtra("setImgname2");
            image3 = intent.getStringExtra("setImgname3");
            content = intent.getStringExtra("content");
            if(year == null && weather == null && id == 0) {
                // id, year, weather 값이 넘어오지 않아서 false 리턴
                return false;
            }
        }
        return true;
    }
    public void draw(){
        view_year.setText(year);
        drawWeather(weather);
        drawImages(image1, image2, image3);
        drawRating(q1, q2, q3);
        view_content.setText(content);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        if (menu_id == R.id.view_close) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if(menu_id == R.id.view_update) {
            //id를 first쪽으로 보낸다
            Intent intent = new Intent(getApplicationContext(), NoteAdd.class);
            intent.putExtra("id", id);
            intent.putExtra("year", year);
            intent.putExtra("weather", weather);
            intent.putExtra("content", content);
            intent.putExtra("q1", q1);
            intent.putExtra("q2", q2);
            intent.putExtra("q3", q3);
            intent.putExtra("image1", image1);
            intent.putExtra("image2", image2);
            intent.putExtra("image3", image3);
            startActivity(intent);
        }else if(menu_id == R.id.view_delete){
            db.todoDao().Selectdelete(id);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            this.finish();
            Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    //출처 https://dev-imaec.tistory.com/10
    //finish하면 다시 프래그먼트로 돌아가기 때문에 startActivity를 이용해 메인으로 돌아가게 만듬
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 리스트로 갑니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            toast.cancel();
            this.finish();
        }
    }

}

