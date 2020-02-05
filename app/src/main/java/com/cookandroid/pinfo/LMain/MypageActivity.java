package com.cookandroid.pinfo.LMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cookandroid.pinfo.Mypage.AlarmActivity;
import com.cookandroid.pinfo.Mypage.BoxActivity;
import com.cookandroid.pinfo.Mypage.CalendarActivity;
import com.cookandroid.pinfo.Mypage.CheckActivity;
import com.cookandroid.pinfo.R;

public class MypageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_mypage);
        setTitle("PInfo 의약품 정보 알리미");

        Button b_lm_box = (Button) findViewById(R.id.b_lm_box);
        Button b_lm_alarm = (Button) findViewById(R.id.b_lm_alarm);
        Button b_lm_check = (Button) findViewById(R.id.b_lm_check);
        Button b_lm_schedule = (Button) findViewById(R.id.b_lm_sche);

        b_lm_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
                startActivity(intent);
            }
        });

        b_lm_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        b_lm_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                startActivity(intent);
            }
        });

        b_lm_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

    }
}
