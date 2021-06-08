package com.kykj.haru2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;



public class Setting extends AppCompatActivity {
    private AppDatabase db;
    LinearLayout backup_restore, export, opinion, rating_opinion, friend_share,all_delete;
    Switch password, alram;
    TextView setting_day;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 툴바의 홈버튼의 이미지를 변경
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        //룸을 사용하기 위한 초기화(todo-db를 사용한다는 뜻과 같습니다.)
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "todo-db").allowMainThreadQueries().build();
        Integer date;
        //레이아웃 정의
        backup_restore = (LinearLayout)findViewById(R.id.backup_restore);
        export = (LinearLayout)findViewById(R.id.export);
        opinion = (LinearLayout)findViewById(R.id.opinion);
        rating_opinion = (LinearLayout)findViewById(R.id.rating_opinion);
        friend_share = (LinearLayout)findViewById(R.id.friend_share);
        all_delete = (LinearLayout)findViewById(R.id.all_delete);

        //스위치 정의
        password = (Switch)findViewById(R.id.password);
        alram = (Switch)findViewById(R.id.alram);
        setting_day = (TextView)findViewById(R.id.setting_day);
        try {
             date = db.todoDao().Countselect();
            setting_day.setText(date.toString());
        }catch (Exception e){
            System.out.println("실패");
            System.out.println(e);
        }

        backup_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"백업/복원 기능은 준비중인 기능입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"내보내기 기능은 준비중인 기능입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"리뷰남기기 기능은 준비중인 기능입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        rating_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"별점 리뷰남기기 기능은 준비중인 기능입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        friend_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"공유 기능은 준비중인 기능입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        final Integer finalDate = db.todoDao().Countselect();
        all_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(Setting.this);
                dlg.setTitle("모든 노트 초기화"); //제목
                dlg.setMessage("정말 모든 노트를 초기화 하시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.haru_logo); // 아이콘 설정
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        System.out.println(finalDate);
                        if(finalDate <= 0){
                            Toast.makeText(Setting.this,"작성된 노트가 아무것도 없어요 :)",Toast.LENGTH_SHORT).show();
                        }else{
                            db.todoDao().all_delete();
                            Toast.makeText(Setting.this,"모든 노트를 삭제했어요! :(",Toast.LENGTH_SHORT).show();

                            //삭제시 메인으로 돌아가게 됩니다.
                            Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(refresh);
                            Setting.this.finish();
                        }

                    }


                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //토스트 메시지
                        Toast.makeText(Setting.this,"취소를 눌르셨습니다. :)",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();

            }
        });

        password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Toast.makeText(getApplicationContext(),"비밀번호 활성화는 현재 구현중에 있습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"비밀번호 비활성화는 현재 구현중에 있습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Toast.makeText(getApplicationContext(),"알람 활성화는 현재 구현중에 있습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"알람 비활성화는 현재 구현중에 있습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

           case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
