package com.kykj.haru2;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import android.os.DropBoxManager;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ForFragment extends Fragment {
    private final int PICTURE_REQUEST_CODE = 200;
    private AppDatabase db;
    // Store instance variables


    private Uri[] imageURI;
    private ImageView[] imageView;
    private FrameLayout[] imageFrame;
    private ImageButton[] imageClose;

    // activity(fragment) components
    private LinearLayout ibn1;
    //private ImageView image1,image2,image3;
    //private FrameLayout one_frame,two_frame,three_frame;
    private EditText content;
    private Button btn1;
    //private ImageButton one_close, two_close, three_close;

    private float startTotal,startTotal2,startTotal3;
    private String result,result2,page, Year, Weather;
    //private Uri uri, urione;
    //private ClipData clipData;
    int id;
    String update_image1 , update_image2, update_image3, update_content;


    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //@Subscribe(threadMode = ThreadMode.BACKGROUND) 이벤트를 받은 후 백그라운드에서 작업을 실행함
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void testEvent(FirstFragment.DataEvent event){
        Year = event.helloEventBus;
        System.out.println(Year);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void testEvent2(SecondFragment.DataEvent event){
        Weather = event.WeatherEventBus;
        System.out.println(Weather);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void testEvent3(ThirdFragment.DataEvent event){
        startTotal = event.startTotal;
        startTotal2 = event.startTotal2;
        startTotal3 = event.startTotal3;
        System.out.println("-----------------------------");
        Log.d(String.valueOf(startTotal),"Tag4");
        Log.d(String.valueOf(startTotal2),"Tag4");
        Log.d(String.valueOf(startTotal3),"Tag4");
        System.out.println("-----------------------------");
    }

    //인수로 조각을 만들기위한 newInstance 생성자
    public static ForFragment newInstance(
            String page, int id, String content, String image1,
            String image2,String image3
    ) {
        ForFragment fragment = new ForFragment();
        Bundle args = new Bundle();
        args.putString("someInt", page);
        args.putInt("update_id", id);
        args.putString("update_content", content);
        args.putString("update_image1", image1);
        args.putString("update_image2", image2);
        args.putString("update_image3", image3);
        fragment.setArguments(args);
        return fragment;
    }

    // 전달 된 인수를 기반으로 인스턴스 변수 저장
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            EventBus.getDefault().register(this);
        } catch (Exception e) {

        }

        if (getArguments() != null) {
            id = getArguments().getInt("update_id");
            update_content = getArguments().getString("update_content");
            update_image1 = getArguments().getString("update_image1");
            update_image2 = getArguments().getString("update_image2");
            update_image3 = getArguments().getString("update_image3");


// CharSequence
//            ClipData.Item item1 = new ClipData.Item(Uri.parse(update_image1));
//            ClipData.Item item2 = new ClipData.Item(Uri.parse(update_image2));
//            ClipData.Item item3 = new ClipData.Item(Uri.parse(update_image3));
//            String[] mimeType = {
//                    "image/*"
//            };
//            uri = Uri.parse(update_image1);
//            ClipData updateClips = new ClipData(null, mimeType, item1);
//            updateClips.addItem(item2);
//            updateClips.addItem(item3);
//            this.clipData = updateClips;

        } else {
            System.out.println("오류 발생");
        }

    }

    private void changeImage() {
        for(int i=0; i<3; i++) {
            imageView[i].setImageResource(0);
            imageURI[i] = null;
        }
    }
    private void changeImage(Uri uri1, Uri uri2, Uri uri3) {
        this.changeImage();
        if(uri1 != null) {
            imageView[0].setImageURI(uri1);
            imageFrame[0].setVisibility(View.VISIBLE);
            imageURI[0] = uri1;
        }
        if(uri2 != null) {
            imageView[1].setImageURI(uri2);
            imageFrame[1].setVisibility(View.VISIBLE);
            imageURI[1] = uri2;
        }
        if(uri3 != null) {
            imageView[2].setImageURI(uri3);
            imageFrame[2].setVisibility(View.VISIBLE);
            imageURI[2] = uri3;
        }
    }
    private void changeImage(ClipData chip) {
        this.changeImage();
        int chipSize = chip.getItemCount();
        for(int i=0; i<chipSize; i++) {
            Uri uri = chip.getItemAt(i).getUri();
            imageView[i].setImageURI(uri);
            imageFrame[i].setVisibility(View.VISIBLE);
            imageURI[i] = uri;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 사진 선색 창에서 돌아옴
        if(requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //기존 이미지 지우기
                this.changeImage();
                //image1.setImageResource(0);
                //image2.setImageResource(0);
                //image3.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();
                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData != null) {
                    System.out.println("사진 여러장.");
                    this.changeImage(clipData);
                    /* 위 코드로 대체
                    for(int i = 0; i < 3; i++) {
                        if(i<clipData.getItemCount()) {
                            imageURI[i] = clipData.getItemAt(i).getUri();
                            //urione =  clipData.getItemAt(i).getUri();
                            switch (i) {
                                case 0:
                                image1.setImageURI(urione);
                                one_frame.setVisibility(View.VISIBLE);
                                break;
                                case 1:
                                image2.setImageURI(urione);
                                two_frame.setVisibility(View.VISIBLE);
                                break;
                                case 2:
                                image3.setImageURI(urione);
                                three_frame.setVisibility(View.VISIBLE);
                                //ibn1.setVisibility(View_memo.GONE);
                                break;
                            }
                        }
                    }
                    */
                } else if(uri != null) {
                    System.out.println("사진1장.");
                    this.changeImage(uri, null, null);
                    /* 위 코드로 대체
                    image1.setImageURI(uri);
                    one_frame.setVisibility(View.VISIBLE);
                    */
                } else {
                    System.out.println("사진없음.");
                    this.changeImage();
                }
            }
        }
    }
    // Inflate the view for the fragment based on layout XML
    //이 기능은 롤리팝 이상부터 작동합니다.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        imageURI = new Uri[3];
        imageClose = new ImageButton[3];
        imageClose[0] = (ImageButton) view.findViewById(R.id.one_close);
        imageClose[1] = (ImageButton) view.findViewById(R.id.two_close);
        imageClose[2] = (ImageButton) view.findViewById(R.id.three_close);
        /*
        one_close = (ImageButton) view.findViewById(R.id.one_close);
        two_close = (ImageButton) view.findViewById(R.id.two_close);
        three_close = (ImageButton) view.findViewById(R.id.three_close);
        */

        ibn1 = (LinearLayout)view.findViewById(R.id.ibn1);
        btn1 = (Button)view.findViewById(R.id.btn1);

        imageFrame = new FrameLayout[3];
        imageFrame[0] = (FrameLayout) view.findViewById(R.id.one_frame);
        imageFrame[1] = (FrameLayout) view.findViewById(R.id.two_frame);
        imageFrame[2] = (FrameLayout) view.findViewById(R.id.three_frame);
        /*
        one_frame = (FrameLayout)view.findViewById(R.id.one_frame);
        two_frame = (FrameLayout)view.findViewById(R.id.two_frame);
        three_frame = (FrameLayout)view.findViewById(R.id.three_frame);
        */
        imageView = new ImageView[3];
        imageView[0] = (ImageView) view.findViewById(R.id.first_img);
        imageView[1] = (ImageView) view.findViewById(R.id.second_img);
        imageView[2] = (ImageView) view.findViewById(R.id.third_img);
        /*
        image1 = (ImageView)view.findViewById(R.id.first_img);
        image2 = (ImageView)view.findViewById(R.id.second_img);
        image3 = (ImageView)view.findViewById(R.id.third_img);
        */
        //이미지 배경 및 이미지 저장
        //setClipToOutline 하는 이유 xml에서는 불가능하지만 자바에서 이 설정을 하면 내가 만들어둔 배경(xml)으로 적용할 수 있음
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.shape2);
        for(int i=0; i<3; i++) {
            imageView[i].setBackground(drawable);
            imageView[i].setClipToOutline(true);
            //사진을 길게 클릭시 삭제버튼이 생기고 x버튼을 클릭시 삭제 가능
            final ImageButton targetCloseButton = imageClose[i];
            final int currentIndex = i;
            imageView[i].setOnLongClickListener(new View.OnLongClickListener() {
                final ImageButton closeButton = targetCloseButton;
                @Override
                public boolean onLongClick(View view) {
                    closeButton.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            imageClose[i].setOnClickListener(new View.OnClickListener() {
                final int index = currentIndex;
                @Override
                public void onClick(View view) {
                    dropImage(index);
                    //ibn1.setVisibility(View_memo.VISIBLE);
                }
            });
        }
        /*
        image1.setBackground(drawable);
        image1.setClipToOutline(true);
        image2.setBackground(drawable);
        image2.setClipToOutline(true);
        image3.setBackground(drawable);
        image3.setClipToOutline(true);
        */
        this.changeImage();
        // 이미지
        if(id > 0) {
            try {
              /*
                InputStream input = getActivity().getContentResolver().openInputStream(Uri.parse(update_image1));
                //비트맵으로 DECODE
                Bitmap mSelectedPhotoBmp = BitmapFactory.decodeStream(input);
  */
                Uri image1URI = !update_image1.equals("") ? Uri.parse(update_image1) : null;
                Uri image2URI = !update_image2.equals("") ? Uri.parse(update_image2) : null;
                Uri image3URI = !update_image3.equals("") ? Uri.parse(update_image3) : null;
                this.changeImage(
                        image1URI,
                        image2URI,
                        image3URI
                );
                // 위 코드로 대체
                //image2.setImageURI(Uri.parse(update_image2));
                //image3.setImageURI(Uri.parse(update_image3));
                /*
                if(update_image1 != null && update_image2.equals("") && update_image3.equals("")) {
                    one_frame.setVisibility(View.VISIBLE);
                } else if(update_image1 != null && update_image2 != null && update_image3.equals("")) {
                    one_frame.setVisibility(View.VISIBLE);
                    two_frame.setVisibility(View.VISIBLE);
                } else if(update_image1 != null && update_image2 != null && update_image3 != null) {
                    one_frame.setVisibility(View.VISIBLE);
                    two_frame.setVisibility(View.VISIBLE);
                    three_frame.setVisibility(View.VISIBLE);
                }
                */
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.changeImage();
            /* 위 코드로 대체
            image1.setImageResource(0);
            image2.setImageResource(0);
            image3.setImageResource(0);
            */
        }



        ibn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                //겔러리로 진입하기위한 인텐트
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //타입은 이미지만
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
                Toast.makeText(getActivity(),"사진은 3장만 가능합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // EditText에 들어갈 내용
        content = (EditText)view.findViewById(R.id.content);
        if(id > 0){
            content.setText(update_content);
        }else{
            content.setText("");
        }

/*
        image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                two_close.setVisibility(View.VISIBLE);
                return false;
            }
        });
        two_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image2.setImageResource(0);
                two_frame.setVisibility(View.GONE);
                two_close.setVisibility(View.GONE);
               // ibn1.setVisibility(View_memo.VISIBLE);
            }
        });

        image3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                three_close.setVisibility(View.VISIBLE);
                return false;
            }
        });
        three_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image3.setImageResource(0);
                three_frame.setVisibility(View.GONE);
                three_close.setVisibility(View.GONE);
                //ibn1.setVisibility(View_memo.VISIBLE);
            }
        });
*/
        //뷰 터치시 키보드 아래로 내리기
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
                return false;
            }
        });
        //저장하기 버튼을 위한 설정
        if(id > 0){
            btn1.setText("수정하기");
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Year == null) {
                    Log.i("fragment", "날짜 정보가 없어 중지됨.");
                    Log.i("fragment", "" + Year);
                    return;
                }
                //이벤트 버스에서 받아온 year 값
                String text = Year;
                //날짜 정규식 잘못 입력하였을 시 앱 꺼짐을 방지
                String pattern = "^[0-9][0-9][0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9]$"; // ^시작,$
                // 끝
                if (text != null && !text.matches(pattern)) {// (년도-월-일) 의 패턴 혹은 다르게 다른 값이 넘어오는지 체크
                    Toast.makeText(getActivity(),"날짜를 잘못 쓰셨어요! 첫 페이지를 확인해주세요 :)", Toast.LENGTH_SHORT).show();
                }else{
                    //todo 객체
                    Todo todo = new Todo();
                    Log.i("fragment", "" + Year);
                    //날짜 저장 개행문제 있을시 제거
                    todo.setYear(Year.replace("\n",""));
                    //컨텐츠 저장
                    todo.setContent(content.getText().toString());
                    //날씨가 들어왔는지 체크
                    if(Weather == null){
                        Toast.makeText(getActivity(),"날씨가 선택되지 않았어요! 두 번째 페이지를 확인해주세요 :)", Toast.LENGTH_SHORT).show();
                    }else{
                        //잘 넘어왔다면 저장
                        todo.setWeather(Weather);
                        todo.setStar_three(startTotal3);
                        todo.setStar_two(startTotal2);
                        todo.setStar_one(startTotal);
                        //이곳이 중요
                        //클립 데이타 안에는 이미지 url이 저장되어 있다.
                        if(imageURI[0] != null) {
                            todo.setImgname1(imageURI[0].toString());
                        }
                        if(imageURI[1] != null) {
                            todo.setImgname2(imageURI[1].toString());
                        }
                        if(imageURI[2] != null) {
                            todo.setImgname3(imageURI[2].toString());
                        }
                        if(id > 0){
                            // 수정
                            String imageString1 = imageURI[0] != null ? imageURI[0].toString() : null,
                                    imageString2 = imageURI[1] != null ? imageURI[1].toString() : null,
                                    imageString3 = imageURI[2] != null ? imageURI[2].toString() : null;
                            db.todoDao().UpdateImg(id,
                                    imageString1,
                                    imageString2,
                                    imageString3
                            );
                            db.todoDao().SelectUpdate(id,Year, Weather, (startTotal != 0.0 ? startTotal: 0.0),(startTotal2 != 0.0 ? startTotal2: 0.0),(startTotal3 != 0.0 ? startTotal3: 0.0), content.getText().toString());
                            Toast.makeText(getContext(),"수정되었습니다. :)",Toast.LENGTH_SHORT).show();
                        } else {
                            //추가
                            db.todoDao().insert(todo);//todo 디비에 입력 시킨다.
                            Toast.makeText(getContext(),"저장되었습니다. :)",Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(getActivity().getApplicationContext(), View_memo.class);
                        intent.putExtra("update_id", id);
                        intent.putExtra("year", Year);
                        intent.putExtra("weather", Weather);
                        intent.putExtra("startTotal", startTotal);
                        intent.putExtra("startTotal2", startTotal2);
                        intent.putExtra("startTotal3", startTotal3);

                        if(imageURI[0] != null) {
                            intent.putExtra("setImgname1", imageURI[0].toString());
                        }
                        if(imageURI[1] != null) {
                            intent.putExtra("setImgname2", imageURI[1].toString());
                        }
                        if(imageURI[2] != null) {
                            intent.putExtra("setImgname3", imageURI[2].toString());
                        }
                        intent.putExtra("content", content.getText().toString());
                        startActivity(intent);
                        getActivity().finish();
                    }
                }

            }
        });
        //룸을 사용하여 todo-db에 초기화 하고 다시만듬
        db = Room.databaseBuilder(getActivity(),AppDatabase.class, "todo-db").allowMainThreadQueries().build();
        return view;
    }
    private void dropImage(int index) {
        if(imageURI[index] == null) {
            return;
        }
        for(int i = index; i<3; i++) {
            if(i == 2) {
                imageURI[i] = null;
                imageView[i].setImageResource(0);
                imageFrame[i].setVisibility(View.GONE);
                imageClose[index].setVisibility(View.GONE);
            } else {
                if(imageURI[i] == null) {
                    break;
                }
                if(imageURI[i+1] == null) {
                    imageURI[i] = null;
                    imageView[i].setImageResource(0);
                    imageFrame[i].setVisibility(View.GONE);
                    imageClose[index].setVisibility(View.GONE);
                } else {
                    imageURI[i] = imageURI[i+1];
                    imageView[i].setImageURI(imageURI[i+1]);
                }
            }
        }
    }
}