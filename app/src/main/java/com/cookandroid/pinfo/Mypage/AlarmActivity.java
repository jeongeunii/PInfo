package com.cookandroid.pinfo.Mypage;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cookandroid.pinfo.R;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent1;
    PendingIntent pending_intent2;
    int choose_whale_sound;
    CheckBox check_on;
    int ClickCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        this.context = this;

        // 알람매니저 설정
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // 타임피커 설정
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);


        update_text = (TextView) findViewById(R.id.update_text);

        // Calendar 객체 생성
        final Calendar calendar = Calendar.getInstance();

        // 알람리시버 intent 생성
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        final CheckBox check_on = (CheckBox)findViewById(R.id.check_on);


        // 스피너 ui
        Spinner spinner = (Spinner) findViewById(R.id.richard_spinner);
        // array 파일의 스피너 적용
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.whale_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 적용
        spinner.setAdapter(adapter);
        // 클릭시 the onItemSelected method 적용
        spinner.setOnItemSelectedListener(this);


        // 알람 시작 버튼
        final Button alarm_on = (Button) findViewById(R.id.alarm_on);


        alarm_on.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                //날짜안의시간선택
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                // 시간 가져오기
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                long oneday = 24 * 60 * 60 * 1000; //24시간 반복


                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //12시간으로 맞추기
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    //10:7 --> 10:07
                    minute_string = "0" + String.valueOf(minute);
                }

                //버튼 클릭시 설정된 알람 시간 분 출력
                set_alarm_text("알람 설정: " + hour_string + ":" + minute_string);

                // reveiver에 string 값 넘겨주기
                my_intent.putExtra("extra", "alarm on");


                my_intent.putExtra("whale_choice", choose_whale_sound);
                Log.e("The whale id is" , String.valueOf(choose_whale_sound));

                //24시간 반복 선택시

                if(check_on.isChecked()) {
                    pending_intent1 = PendingIntent.getBroadcast(AlarmActivity.this, 0,
                            my_intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    // 알람 셋팅 부팅 이후의 경과 시간 지정
                    //serRepating은 반복성
                    alarm_manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis()
                            ,oneday,pending_intent1);


                }
                else {
                    pending_intent1 = PendingIntent.getBroadcast(AlarmActivity.this, 0,
                            my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // 알람 셋팅 세계 시간에 맞춰 즉시 깨운다
                    //.set는 1회성
                    alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            pending_intent1);

                }


               /*  pending_intent1 = PendingIntent.getBroadcast(AlarmActivity.this, 0,
                         my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                 // 알람 셋팅
                 alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24
                         pending_intent1);



                 pending_intent2 = PendingIntent.getBroadcast(AlarmActivity.this, 1,
                         my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                 // 알람 셋팅
                 alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                         pending_intent2); */

            }



        });



        // 알람 정지버튼
        Button alarm_off = (Button) findViewById(R.id.alarm_off);


        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                set_alarm_text("알람을 종료하였습니다");

                // 알람 매니저 취소
                alarm_manager.cancel(pending_intent1);

                //알람 취소 버튼 눌렀을때
                my_intent.putExtra("extra", "alarm off");

                my_intent.putExtra("whale_choice", choose_whale_sound);


                // 알람취소
                sendBroadcast(my_intent);


            }
        });



    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        choose_whale_sound = (int) id;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}