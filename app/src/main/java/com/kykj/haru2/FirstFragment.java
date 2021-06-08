package com.kykj.haru2;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.greenrobot.eventbus.EventBus;


public class FirstFragment extends Fragment {
     String title;
     EditText year;
     LinearLayout focus;
     View view;
     int id;
     String imgid , image1 ,image2, image3 , years, weather, content;
     Float q1, q2 ,q3;


    public static class DataEvent {

        public final String helloEventBus;

        public DataEvent(String helloEventBus) {
            this.helloEventBus = helloEventBus;
        }
    }
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //인수로 조각을 만들기위한 newInstance 생성자
    //이곳은 수정을 위해 값을 받아 오는 곳 입니다.
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       if(id > 0) {
           System.out.println(years);
           System.out.println(weather);
           System.out.println(content);
           System.out.println(q1);
           System.out.println(q2);
           System.out.println(q3);
           System.out.println(image1);
           System.out.println(image2);
           System.out.println(image3);
       }else {
           if (getArguments() != null) {
               title = getArguments().getString("someTitle");
           } else {
               System.out.println("오류 발생");
           }
           //레이아웃지정
           view = inflater.inflate(R.layout.fragment_first, container, false);
           year = (EditText) view.findViewById(R.id.year);

           focus = (LinearLayout) view.findViewById(R.id.focus);
           //view 터치시 키보드 숨기기
           view.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View view, MotionEvent motionEvent) {

                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(year.getWindowToken(), 0);
                   return false;
               }
           });


           year.setText(title);
           String dataString = title;
           EventBus.getDefault().post(new DataEvent(dataString));
           onDestroy();
           year.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View view, MotionEvent motionEvent) {
                   year.setText("");
                   year.setHint(Html.fromHtml("<small><small><small>" + title + " 형식이 같게 적어주세요" + "</small></small></small>"));
                   return false;
               }
           });

           year.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence s, int i, int i1, int i2) {


               }

               @Override
               public void afterTextChanged(Editable editable) {
                   //다 입력된 후에 이벤트 버스를 통해 값이 저장됌
                   String dataString = editable.toString();
                   EventBus.getDefault().post(new DataEvent(dataString));
                   onDestroy();

               }
           });
       }
           return view;

    }



}
