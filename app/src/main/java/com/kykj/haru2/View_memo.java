package com.kykj.haru2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class View_memo extends AppCompatActivity {
    private AppDatabase db;
    TextView view_year ,view_content;
    ImageView view_weather;
    RatingBar start1, start2 , start3;
    Bitmap[] mSelectedPhotoBmp;
    Activity activity;
    String imgid , image1 ,image2, image3 , year, weather, content;
    String sendimgid , sendimage1 ,sendimage2, sendimage3 , sendyear, sendweather, sendcontent;
    Float q1, q2 ,q3;
    Float sendq1, sendq2 ,sendq3;
    long backKeyPressedTime = 0;
    Toast toast;
    Uri uri1, uri2, uri3;
    LayoutInflater inflater;
    String[] imgCount;
    int id;


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
            fetch();
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
    public void fetch(){
        Intent intent = getIntent();
        //만약 id 값이 0이면 아래 코드 실행 0 이상이면 아이디값으로 불러온 쿼리의 값으로 출력해야함
        id = intent.getIntExtra("View_id", 0);
        System.out.println(id);

        if(id > 0){
            List<Todo> list = (List<Todo>) db.todoDao().Selectid(id);
            for(final Todo todo : list){
                sendyear = todo.getYear();
                sendweather = todo.getWeather();
                sendq1 = todo.getStar_one();
                sendq2 = todo.getStar_two();
                sendq3 = todo.getStar_three();
                sendimage1 = todo.getImgname1();
                sendimage2 = todo.getImgname2();
                sendimage3 = todo.getImgname3();
                sendcontent = todo.getContent();

                view_year.setText(todo.getYear());
                if (todo.getWeather().equals("화창했어요.")) {
                    view_weather.setImageResource(R.drawable.w_s_son);
                } else if (todo.getWeather().equals("비가 왔어요.")) {
                    view_weather.setImageResource(R.drawable.w_s_rain);
                } else if (todo.getWeather().equals("흐렸어요.")) {
                    view_weather.setImageResource(R.drawable.w_s_cloud);
                } else if (todo.getWeather().equals("눈이 왔어요.")) {
                    view_weather.setImageResource(R.drawable.w_s_snow);
                } else if (todo.getWeather().equals("번개가 쳤어요.")) {
                    view_weather.setImageResource(R.drawable.w_s_bunge);
                }
                System.out.println(todo.getImgname1()+"이미지1");
                System.out.println(todo.getImgname2()+"이미지2");
                System.out.println(todo.getImgname3()+"이미지3");
                if(todo.getImgname1() != null) {
                    try {

                        if (todo.getImgname1() != null && todo.getImgname2() == null && todo.getImgname3() == null) {
                            imgid = "one_image";
                            uri1 = Uri.parse(todo.getImgname1());
                            System.out.println("one_image");
                            //이미지의 CONTENT://주소를 스트림 주소로 변경
                            InputStream input = this.getContentResolver().openInputStream(uri1);
                            //비트맵으로 DECODE
                            mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input)};
                        } else if (todo.getImgname1() != null && todo.getImgname2() != null && todo.getImgname3() == null) {
                            imgid = "two_image";
                            System.out.println("two_image");
                            uri1 = Uri.parse(todo.getImgname1());
                            uri2 = Uri.parse(todo.getImgname2());
                            InputStream input = this.getContentResolver().openInputStream(uri1);
                            InputStream input2 = this.getContentResolver().openInputStream(uri2);
                            mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2)};
                        } else if (todo.getImgname1() != null && todo.getImgname2() != null && todo.getImgname3() != null) {
                            imgid = "three_image";
                            System.out.println("three_image");
                            uri1 = Uri.parse(todo.getImgname1());
                            uri2 = Uri.parse(todo.getImgname2());
                            uri3 = Uri.parse(todo.getImgname3());
                            //이미지의 CONTENT://주소를 스트림 주소로 변경
                            InputStream input = this.getContentResolver().openInputStream(uri1);
                            InputStream input2 = this.getContentResolver().openInputStream(uri2);
                            InputStream input3 = this.getContentResolver().openInputStream(uri3);

                            //비트맵으로 DECODE
                            mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2), BitmapFactory.decodeStream(input3)};
                            System.out.println(Arrays.toString(mSelectedPhotoBmp));
                        }else{
                            System.out.println(todo.getImgname1()+"여기 ?");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //이미지 슬라이더를 위한 페이지 어뎁터
                    PagerAdapter pagerAdapter = new PagerAdapter() {
                        //갯수를 알기 위해 배열에 담는다.




                        @Override
                        public int getCount() {
                            if (imgid.equals("one_image")) {
                                imgCount = new String[]{todo.getImgname1()};
                            } else if (imgid.equals("two_image")) {
                                imgCount = new String[]{todo.getImgname1(), todo.getImgname2()};
                            } else if (imgid.equals("three_image")) {
                                imgCount = new String[]{todo.getImgname1(), todo.getImgname2(), todo.getImgname3()};
                            }
                            return imgCount.length;
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
                }else {
                    LinearLayout img_slider_view = (LinearLayout)findViewById(R.id.img_slider_view);
                    img_slider_view.setVisibility(View.GONE);
                }
                start1.setRating(todo.getStar_one());
                start2.setRating(todo.getStar_two());
                start3.setRating(todo.getStar_three());
                view_content.setText(todo.getContent());
            }
        }else {

            year = intent.getStringExtra("year");
            weather = intent.getStringExtra("weather");
            content = intent.getStringExtra("content");
            image1 = intent.getStringExtra("setImgname1");
            image2 = intent.getStringExtra("setImgname2");
            image3 = intent.getStringExtra("setImgname3");
            System.out.println(image1+"img1");
//            System.out.println(image2.trim().equals("")); // "" / null
//            System.out.println(image3.trim() == null);
            q1 = (float) intent.getFloatExtra("startTotal", 0.0f);
            q2 = (float) intent.getFloatExtra("startTotal2", 0.0f);
            q3 = (float) intent.getFloatExtra("startTotal3", 0.0f);
            view_year.setText(year);

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

            if(image1 == null) {
                image1 = "";
            }
            if(image2 == null) {
                image2 = "";
            }
            if(image3 == null) {
                image3 = "";
            }

            if(!image1.equals("")) {
                System.out.println("씨발");
                try {
                    if (!(image1.equals("") || image2.equals("") || image3.equals(""))) {

                        imgid = "three_image";
                        uri1 = Uri.parse(image1);
                        uri2 = Uri.parse(image2);
                        uri3 = Uri.parse(image3);
                        //이미지의 CONTENT://주소를 스트림 주소로 변경
                        InputStream input = this.getContentResolver().openInputStream(uri1);
                        InputStream input2 = this.getContentResolver().openInputStream(uri2);
                        InputStream input3 = this.getContentResolver().openInputStream(uri3);
                        //비트맵으로 DECODE
                        mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2), BitmapFactory.decodeStream(input3)};
                        System.out.println(image3+"ssibal3");

                    } else if (!(image1.equals("") || image2.equals(""))) {

                        imgid = "two_image";
                        uri1 = Uri.parse(image1);
                        uri2 = Uri.parse(image2);
                        InputStream input = this.getContentResolver().openInputStream(uri1);
                        InputStream input2 = this.getContentResolver().openInputStream(uri2);
                        mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input), BitmapFactory.decodeStream(input2)};
                        System.out.println(image2+"ssibal2");

                    } else if (!image1.equals("")) {

                        imgid = "one_image";
                        uri1 = Uri.parse(image1);
                        //이미지의 CONTENT://주소를 스트림 주소로 변경
                        InputStream input = this.getContentResolver().openInputStream(uri1);
                        //비트맵으로 DECODE
                        mSelectedPhotoBmp = new Bitmap[]{BitmapFactory.decodeStream(input)};
                        System.out.println(image1+"ssibal");
                    }

                    System.out.println(uri1);
                    System.out.println(uri2);
                    System.out.println(uri3);
                } catch (Exception e) {
                    System.out.println(e);
                }
                //이미지 슬라이더를 위한 페이지 어뎁터
                PagerAdapter pagerAdapter = new PagerAdapter() {
                    //갯수를 알기 위해 배열에 담는다.


                    @Override
                    public int getCount() {
                        if (imgid.equals("one_image")) {
                            imgCount = new String[]{image1};
                        } else if (imgid.equals("two_image")) {
                            imgCount = new String[]{image1, image2};
                        } else if (imgid.equals("three_image")) {
                            imgCount = new String[]{image1, image2, image3};
                        }
                        return imgCount.length;
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
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageBitmap(mSelectedPhotoBmp[position]);
                        container.addView(view);
                        return view;
                    }

                    @Override
                    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                        container.invalidate();
                    }
                };



                System.out.println(year);
                System.out.println(weather);
                System.out.println(content);
                System.out.println(q1);
                System.out.println(q2);
                System.out.println(q3);
                System.out.println(image1);
                System.out.println(image2);
                System.out.println(image3);
                ViewPager viewPager = findViewById(R.id.pager);
                viewPager.setAdapter(pagerAdapter);
                CircleIndicator indicator;
                indicator = findViewById(R.id.indicator);
                indicator.setViewPager(viewPager);
            }else {
                LinearLayout img_slider_view = (LinearLayout)findViewById(R.id.img_slider_view);
                img_slider_view.setVisibility(View.GONE);
            }
            start1.setRating(q1);
            start2.setRating(q2);
            start3.setRating(q3);
            view_content.setText(content);
        }
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
        }else if(menu_id == R.id.view_update){
            //id를 first쪽으로 보낸다


            Intent intent = new Intent(getApplicationContext(), NoteAdd.class);
            intent.putExtra("id",id);
            intent.putExtra("year", sendyear);
            intent.putExtra("weather", sendweather);
            intent.putExtra("content", sendcontent);
            intent.putExtra("q1", sendq1);
            intent.putExtra("q2", sendq2);
            intent.putExtra("q3", sendq3);
            intent.putExtra("image1", sendimage1);
            intent.putExtra("image2", sendimage2);
            intent.putExtra("image3", sendimage3);

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

