package com.kykj.haru2;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SecondFragment extends Fragment {


    private TextView today_weather_two ,son, rain, snow, cloud, bunge, last, end;
    private ImageView w_son, w_rain, w_snow, w_cloud, w_bunge;

    public static class DataEvent {

        public final String WeatherEventBus;
        //4번째 프로그래먼트로 보내기 위한 데이타버스 이벤트 지정
        public DataEvent(String WeatherEventBus) {
            this.WeatherEventBus = WeatherEventBus;
        }
    }

    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //두번째 프래그먼트 만드는 인스텐스
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        // 임시 값 넘기는 것이라 안보셔도 됩니다.
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        //텍스트뷰 지정

        son = (TextView)view.findViewById(R.id.son);
        rain = (TextView)view.findViewById(R.id.rain);
        snow = (TextView)view.findViewById(R.id.snow);
        cloud = (TextView)view.findViewById(R.id.cloud);
        bunge = (TextView)view.findViewById(R.id.bunge);
        today_weather_two = (TextView)view.findViewById(R.id.today_weather_two);
        last = (TextView)view.findViewById(R.id.last);
        end = (TextView)view.findViewById(R.id.end);

        //이미지뷰 지정
        w_son = (ImageView)view.findViewById(R.id.w_son);
        w_rain = (ImageView)view.findViewById(R.id.w_rain);
        w_snow = (ImageView)view.findViewById(R.id.w_snow);
        w_cloud = (ImageView)view.findViewById(R.id.w_cloud);
        w_bunge = (ImageView)view.findViewById(R.id.w_bunge);

        son.setOnClickListener(new View.OnClickListener() {
            int s = 0; //취소 됐을때와 선택 됐을때의 값을 위해 지정해놓은 값

            @Override
            public void onClick(View view) {
                if(s == 1){
                    w_son.setVisibility(View.GONE);
                    //날씨가 맞다면 왼쪽으로
                    last.setVisibility(View.GONE);
                    //넘겨주세요
                    end.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,40 ,0,0);
                    son.setLayoutParams(lp);
                    son.setText("•화창했어요");
                    //폰트지정
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    son.setTypeface(typeface);
                    //px를 dp로 변환
                    son.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);

                    rain.setVisibility(View.VISIBLE);
                    snow.setVisibility(View.VISIBLE);
                    cloud.setVisibility(View.VISIBLE);
                    bunge.setVisibility(View.VISIBLE);
                    today_weather_two.setVisibility(View.VISIBLE);
                    s = 0; //다시 0으로 지정
                }else{
                    s = s + 1; //선택 된 값
                    w_son.setVisibility(View.VISIBLE);
                    last.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0 ,0,0);

                    LinearLayout.LayoutParams top = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    top.setMargins(0,10,0,0);
                    son.setLayoutParams(lp);
                    w_son.setLayoutParams(top);
                    son.setText("화창했어요.");
                    //폰트 지정
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    son.setTypeface(typeface);
                    //px를 dp로 변환
                    son.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);

                    rain.setVisibility(View.GONE);
                    snow.setVisibility(View.GONE);
                    cloud.setVisibility(View.GONE);
                    bunge.setVisibility(View.GONE);
                    today_weather_two.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SecondFragment.DataEvent(son.getText().toString()));

                }
            }
        });
        //위와 같은 코드이므로 주석처리는 하지 않았습니다.
        rain.setOnClickListener(new View.OnClickListener() {
            int s = 0;
            @Override
            public void onClick(View view) {
                if(s == 1){
                    w_rain.setVisibility(View.GONE);

                    last.setVisibility(View.GONE);
                    end.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,40 ,0,0);
                    rain.setLayoutParams(lp);
                    rain.setText("•비가 왔어요");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    rain.setTypeface(typeface);
                    rain.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);

                    son.setVisibility(View.VISIBLE);
                    snow.setVisibility(View.VISIBLE);
                    cloud.setVisibility(View.VISIBLE);
                    bunge.setVisibility(View.VISIBLE);
                    today_weather_two.setVisibility(View.VISIBLE);
                    s = 0;
                }else{
                    s = s + 1;
                    w_rain.setVisibility(View.VISIBLE);

                    last.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0 ,0,0);

                    LinearLayout.LayoutParams top = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    top.setMargins(0,10,0,0);
                    rain.setLayoutParams(lp);
                    w_rain.setLayoutParams(top);
                    rain.setText("비가 왔어요.");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    rain.setTypeface(typeface);
                    rain.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);

                    son.setVisibility(View.GONE);
                    snow.setVisibility(View.GONE);
                    cloud.setVisibility(View.GONE);
                    bunge.setVisibility(View.GONE);
                    today_weather_two.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SecondFragment.DataEvent(rain.getText().toString()));
                }
            }
        });
        cloud.setOnClickListener(new View.OnClickListener() {
            int s = 0;

            @Override
            public void onClick(View view) {
                if(s == 1){
                    w_cloud.setVisibility(View.GONE);

                    last.setVisibility(View.GONE);
                    end.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,40 ,0,0);
                    cloud.setLayoutParams(lp);
                    cloud.setText("•흐렸어요");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    cloud.setTypeface(typeface);
                    cloud.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);

                    son.setVisibility(View.VISIBLE);
                    snow.setVisibility(View.VISIBLE);
                    rain.setVisibility(View.VISIBLE);
                    bunge.setVisibility(View.VISIBLE);
                    today_weather_two.setVisibility(View.VISIBLE);
                    s = 0;
                }else{
                    s = s + 1;
                    w_cloud.setVisibility(View.VISIBLE);

                    last.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0 ,0,0);

                    LinearLayout.LayoutParams top = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    top.setMargins(0,10,0,0);
                    cloud.setLayoutParams(lp);
                    w_cloud.setLayoutParams(top);
                    cloud.setText("흐렸어요.");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    cloud.setTypeface(typeface);
                    cloud.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);

                    son.setVisibility(View.GONE);
                    rain.setVisibility(View.GONE);
                    snow.setVisibility(View.GONE);
                    bunge.setVisibility(View.GONE);
                    today_weather_two.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SecondFragment.DataEvent(cloud.getText().toString()));
                }
            }
        });
        snow.setOnClickListener(new View.OnClickListener() {
            int s = 0;
            @Override
            public void onClick(View view) {
                if(s == 1){
                    w_snow.setVisibility(View.GONE);

                    last.setVisibility(View.GONE);
                    end.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,40 ,0,0);
                    snow.setLayoutParams(lp);
                    snow.setText("•눈이 왔어요");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    snow.setTypeface(typeface);
                    snow.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);

                    son.setVisibility(View.VISIBLE);
                    cloud.setVisibility(View.VISIBLE);
                    rain.setVisibility(View.VISIBLE);
                    bunge.setVisibility(View.VISIBLE);
                    today_weather_two.setVisibility(View.VISIBLE);
                    s = 0;
                }else{
                    s = s + 1;
                    w_snow.setVisibility(View.VISIBLE);

                    last.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0 ,0,0);

                    LinearLayout.LayoutParams top = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    top.setMargins(0,10,0,0);
                    snow.setLayoutParams(lp);
                    w_snow.setLayoutParams(top);
                    snow.setText("눈이 왔어요.");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    snow.setTypeface(typeface);
                    snow.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);

                    son.setVisibility(View.GONE);
                    rain.setVisibility(View.GONE);
                    cloud.setVisibility(View.GONE);
                    bunge.setVisibility(View.GONE);
                    today_weather_two.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SecondFragment.DataEvent(snow.getText().toString()));
                }
            }
        });
        bunge.setOnClickListener(new View.OnClickListener() {
            int s = 0;
            @Override
            public void onClick(View view) {
                if(s == 1){
                    w_bunge.setVisibility(View.GONE);

                    last.setVisibility(View.GONE);
                    end.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,40 ,0,0);
                    bunge.setLayoutParams(lp);
                    bunge.setText("•번개가 쳤어요");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    bunge.setTypeface(typeface);
                    bunge.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);

                    son.setVisibility(View.VISIBLE);
                    cloud.setVisibility(View.VISIBLE);
                    rain.setVisibility(View.VISIBLE);
                    snow.setVisibility(View.VISIBLE);
                    today_weather_two.setVisibility(View.VISIBLE);
                    s = 0;
                }else{
                    s = s + 1;
                    w_bunge.setVisibility(View.VISIBLE);

                    last.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0 ,0,0);

                    LinearLayout.LayoutParams top = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    top.setMargins(0,10,0,0);
                    bunge.setLayoutParams(lp);
                    w_bunge.setLayoutParams(top);
                    bunge.setText("번개가 쳤어요.");
                    Typeface typeface = getResources().getFont(R.font.spoqa_han_sans_neo_bold);
                    bunge.setTypeface(typeface);
                    bunge.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);

                    son.setVisibility(View.GONE);
                    rain.setVisibility(View.GONE);
                    cloud.setVisibility(View.GONE);
                    snow.setVisibility(View.GONE);
                    today_weather_two.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SecondFragment.DataEvent(bunge.getText().toString()));
                }
            }

        });

        return view;
    }


}
