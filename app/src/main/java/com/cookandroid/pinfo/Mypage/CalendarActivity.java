package com.cookandroid.pinfo.Mypage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cookandroid.pinfo.R;

public class CalendarActivity extends AppCompatActivity {

    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        customCalendarView = (CustomCalendarView) findViewById(R.id.custom_calendar_view);
    }
}
