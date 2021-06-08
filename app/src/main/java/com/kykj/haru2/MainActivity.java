package com.kykj.haru2;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    LinearLayout layout,all_linear, linear_date, linear_Text, rating_view;
    TextView date_text,reView_text;
    FrameLayout frameLayout;
    ImageView roundImg;
    RatingBar ratingBar;
    ImageButton del_imgButton;
    ImageView imageView;
    long backKeyPressedTime = 0;
    Toast toast;



    //ROOM은 롤리팝 이상 버전에서 사용 가능 합니다.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //툴바를 타이틀, 배경을 화이트로 지정한 부분
       toolbar.setBackgroundColor(getResources().getColor(R.color.Actionbar));
       toolbar.setTitleTextColor(getResources().getColor(R.color.Actionbar));
        layout = (LinearLayout)findViewById(R.id.layout);

        //룸을 사용하기 위한 초기화(todo-db를 사용한다는 뜻과 같습니다.)
        db = Room.databaseBuilder(this,AppDatabase.class, "todo-db").allowMainThreadQueries().build();
        try {
            fetch();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.add_plus){
            Intent intent = new Intent(this, NoteAdd.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fetch(){

        Uri uri1;
        List<Todo> list = (List<Todo>) db.todoDao().getAll();

        //전체를 감싸는 스크롤뷰(리니어로 감싸서 하나로 넣기)
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT));

        //리니어 전체 컨테이너 (마진값 넣어야함)
        final LinearLayout all_container_linear = new LinearLayout(this);
        all_container_linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT));
        all_container_linear.setOrientation(LinearLayout.VERTICAL);

        for(final Todo todo : list){

            try{

                    try {
                        //전체적인 내부 컨테이너 (모든것을 담음)
                        all_linear = new LinearLayout(this);
                        all_linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT));
                        all_linear.setOrientation(LinearLayout.VERTICAL);

                        LinearLayout.LayoutParams all_linear_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        all_linear_params.setMargins(16,72,0,40);
                        all_linear.setLayoutParams(all_linear_params);





                            //리니어 레이아웃으로 날짜 쪽 만들기

                            linear_date = new LinearLayout(this);
                            linear_date.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linear_date.setGravity(Gravity.CENTER);
                            linear_date.setOrientation(LinearLayout.HORIZONTAL);



                            // 날짜 왼쪽에 나오는 원
                            roundImg = new ImageView(this);
                            roundImg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            roundImg.setImageResource(R.drawable.w_indicate);
                            roundImg.getLayoutParams().width = 20;
                            roundImg.getLayoutParams().height = 20;
                            roundImg.setScaleType(ImageView.ScaleType.FIT_XY);

                            //날짜 뽑기
                            date_text = new TextView(this);
                            date_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            date_text.setText(todo.getYear());
                            date_text.setTextSize(16);
                            date_text.setTypeface(null, Typeface.BOLD);
                            date_text.setTextColor(Color.BLACK);
                            date_text.setPadding(12, 0, 0, 20);
                            // 날짜 레이아웃 지정

                                System.out.println(todo.getYear().equals(date_text.getText().toString()));
                                linear_date.addView(roundImg);
                                linear_date.addView(date_text);


                            //프레임 레이아웃 정하기
                            frameLayout = new FrameLayout(this);
                            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            FrameLayout.LayoutParams frameLayout_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            frameLayout_params.leftMargin = 36;
                            //프레임 레이아웃 안에 이미지뷰 + RatingBar 넣기
                        if(todo.getImgname1() != null) {
                            uri1 = Uri.parse(todo.getImgname1());
                            //이미지의 CONTENT://주소를 스트림 주소로 변경
                            InputStream input = this.getContentResolver().openInputStream(uri1);
                            //비트맵으로 DECODE
                            Bitmap mSelectedPhotoBmp = BitmapFactory.decodeStream(input);
                            //이미지뷰 동적 생성
                            imageView = new ImageView(this);
                            //이미지 적용
                            imageView.setImageBitmap(mSelectedPhotoBmp);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            //px를 dp로 변경
                            final int Imgwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 372, getResources().getDisplayMetrics());
                            final int Imgheight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                            imageView.getLayoutParams().width = Imgwidth;
                            imageView.getLayoutParams().height = Imgheight;
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            GradientDrawable drawable = (GradientDrawable) getDrawable(R.drawable.shape2);
                            imageView.setBackground(drawable);
                            imageView.setClipToOutline(true);
                            input.close();
                            frameLayout.addView(imageView);
                        }
                            //삭제할 x 이미지버튼 생성
                            del_imgButton = new ImageButton(this);
                            FrameLayout.LayoutParams del_imgParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            del_imgParams.gravity = Gravity.TOP|Gravity.RIGHT;
                            final int del_Imgwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
                            final int del_Imgheight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
                            del_imgParams.width=del_Imgwidth;
                            del_imgParams.height=del_Imgheight;
                            GradientDrawable del_background = (GradientDrawable) getDrawable(R.drawable.circle);
                            del_imgButton.setBackground(del_background);
                            del_imgButton.setImageResource(R.drawable.main_close);
                            del_imgButton.setLayoutParams(del_imgParams);
                            del_imgButton.setVisibility(View.GONE);
                            del_imgButton.setOnClickListener(new View.OnClickListener() {
                                //all_linear의 현재를 담음
                                final LinearLayout myLinearLayout = all_linear;
                                @Override
                                public void onClick(View view) {
                                    db.todoDao().Selectdelete(todo.getId());
                                    db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "todo-db").allowMainThreadQueries().build();
                                    //all_linear의 리무브 뷰해서 마지막에서 다시 불러와진다.
                                    all_container_linear.removeView(myLinearLayout);

                                }
                            });
                            frameLayout.addView(del_imgButton);


                            //평점의 값
                            float star_num = (float) Math.round((todo.getStar_one() + todo.getStar_two() + todo.getStar_three()) / 3);

                            ratingBar = new RatingBar(this,null,android.R.attr.ratingBarStyleSmall);
                            ratingBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ratingBar.setStepSize(Float.parseFloat("0.5"));
                            ratingBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.circle)));
                            ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.circle)));
                            ratingBar.setProgressBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ProgressBackground)));
                            ratingBar.setIsIndicator(true);
                            ratingBar.setRating(star_num);
                            ratingBar.setNumStars(5);


                            //레이팅바 아래로 보내기 위한 params
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.BOTTOM;


                            //레이팅바 뷰 생성 또한 params 생성 마진값을 주기위함
                            rating_view = new LinearLayout(this);
                            rating_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            rating_view.setOrientation(LinearLayout.VERTICAL);

                            LinearLayout.LayoutParams rating_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            rating_params.setMargins(12,0,0,24);
                            ratingBar.setLayoutParams(rating_params);
                            rating_view.addView(ratingBar);


                            //레이아웃 설정

                            rating_view.setLayoutParams(params);

                            frameLayout.addView(rating_view);
                            //사진과 레이팅바 전체 프레임 레이아웃 정의
                            frameLayout.setLayoutParams(frameLayout_params);

                            //리니어 레이아웃으로 본문 쪽 만들기
                            linear_Text = new LinearLayout(this);
                            linear_Text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linear_Text.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout.LayoutParams linear_Text_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            linear_Text_params.setMargins(36,30,0,0);
                             final int linear_Text_width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 372, getResources().getDisplayMetrics());
                            linear_Text_params.width=linear_Text_width;
                            linear_Text.setLayoutParams(linear_Text_params);

                            //본문의 텍스트
                            reView_text = new TextView(this);
                            reView_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            reView_text.setText(todo.getContent());
                            reView_text.setTextSize(14);
                            reView_text.setEms(40);
                            reView_text.setEllipsize(TextUtils.TruncateAt.END);
                            reView_text.setMaxLines(2);
                            reView_text.setTypeface(null, Typeface.NORMAL);

                            linear_Text.addView(reView_text);




                    }
                    catch (Throwable tr) {

                    }

                //전체 리니어 정의

                all_linear.addView(linear_date);
                all_linear.addView(frameLayout);
                all_linear.addView(linear_Text);
                all_container_linear.addView(all_linear);


                //클릭시 View_memo로 id 값을 가져간다. 쿼리로 select 시켜야하기 때문
                all_linear.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), View_memo.class);
                        intent.putExtra("View_id", todo.getId());
                        startActivity(intent);

                    }
                });

            }catch (Exception e){
                System.out.println(e);
            }

            all_linear.setOnLongClickListener(new View.OnLongClickListener() {
                //현재 for문에서 만들었던 del_imgButton을 저장
                final ImageButton del_button = del_imgButton;

                @Override
                public boolean onLongClick(View view) {
                    del_button.setVisibility(View.VISIBLE);
                    Log.d("click","Long");
                    return true;
                }
            });

        }


        //전체 스크롤뷰 끝
        scrollView.addView(all_container_linear);
        layout.addView(scrollView);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();

            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
        //출처 https://calvinjmkim.tistory.com/21
            moveTaskToBack(true);						// 태스크를 백그라운드로 이동
            finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
            toast.cancel();
        }
    }


}
