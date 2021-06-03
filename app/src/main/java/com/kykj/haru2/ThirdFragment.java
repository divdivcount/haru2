package com.kykj.haru2;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RatingBar;


import org.greenrobot.eventbus.EventBus;

public class ThirdFragment extends Fragment {

    private RatingBar q1,q2,q3;
    private float update_q1, update_q2, update_q3;
    String str1,str2,str3;

    public static class DataEvent {

        public final float startTotal;
        public final float startTotal2;
        public final float startTotal3;

        public DataEvent(float startTotal, float startTotal2, float startTotal3) {
            this.startTotal = startTotal;
            this.startTotal2 = startTotal2;
            this.startTotal3 = startTotal3;
        }
    }

    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




    public static ThirdFragment newInstance(int page, float q1, float q2, float q3) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putFloat("q1", q1);
        args.putFloat("q2", q2);
        args.putFloat("q3", q3);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //레이팅 바의 값이 변할 수 있으면로 체인지 리스너를 통해 최종적으로 정해진 값으로 데이터 버스를 통해 값을 넘김
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        q1 = (RatingBar)view.findViewById(R.id.q1);
        q2 = (RatingBar)view.findViewById(R.id.q2);
        q3 = (RatingBar)view.findViewById(R.id.q3);

        if (getArguments() != null) {
            update_q1 = getArguments().getFloat("q1");
            update_q2 = getArguments().getFloat("q2");
            update_q3 = getArguments().getFloat("q3");
        } else {
            System.out.println("오류 발생");
        }
        q1.setRating(update_q1);
        q2.setRating(update_q2);
        q3.setRating(update_q3);
        EventBus.getDefault().post(new ThirdFragment.DataEvent(update_q1,update_q2,update_q3));
        q1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float q1, boolean b) {
                str1 = String.valueOf(q1);
                str1 = Float.toString(q1);

                EventBus.getDefault().post(new ThirdFragment.DataEvent(q1,q2.getRating(),q3.getRating()));

            }
        });
        q2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float q2, boolean b) {
                str2 = String.valueOf(q2);
                str2 = Float.toString(q2);
                EventBus.getDefault().post(new ThirdFragment.DataEvent(q1.getRating(),q2,q3.getRating()));
            }
        });
        q3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float q3, boolean b) {
                str3 = String.valueOf(q3);
                str3 = Float.toString(q3);
                EventBus.getDefault().post(new ThirdFragment.DataEvent(q1.getRating(),q2.getRating(),q3));
            }
        });
        onDestroy();
        return view;
    }
}
