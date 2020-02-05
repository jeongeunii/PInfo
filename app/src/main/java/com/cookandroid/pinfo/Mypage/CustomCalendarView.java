package com.cookandroid.pinfo.Mypage;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cookandroid.pinfo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS =42;
    Calendar calendar = Calendar.getInstance(Locale.KOREA);
    Context context;
    //년도월일
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMMM", Locale.KOREA);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.KOREA);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.KOREA);
    SimpleDateFormat eventDateFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);

    CalendarGridAdapter calendarGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<CalendarEvents> calendarEventsList = new ArrayList<>();

    CalendarDBOpenHelper calendarDbOpenHelper;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        IntializeLayout();
        SetUpCalendar();

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_calendar, null);
                final EditText EventName = (EditText)addView.findViewById(R.id.eventname);
                final TextView EventTime =(TextView) addView.findViewById(R.id.eventtime);
                ImageButton SetTime =(ImageButton) addView.findViewById(R.id.seteventtime);
                Button AddEvent = (Button)addView.findViewById(R.id.addevent);
                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        //알람의 시간과 분을 받아옴
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minuts = calendar.get(Calendar.MINUTE);
                        //알람 event
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog
                                , new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate = new SimpleDateFormat("K:mm a",Locale.KOREA);
                                String event_Time = hformate.format(c.getTime());
                                EventTime.setText(event_Time);
                            }
                        },hours,minuts,false);
                        timePickerDialog.show();
                    }
                });

                final String date = eventDateFormate.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));

                //저장 버튼 눌렀을시 발생
                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(EventName.getText().toString(), EventTime.getText().toString(),date,month,year);
                        SetUpCalendar();
                        alertDialog.dismiss();
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();

            }
        });


        //길게 눌렀을때 약 정보 나오는 부분
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormate.format(dates.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_calendar,null);
                RecyclerView recyclerView = (RecyclerView)showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                CalendarRecycleAdapter calendarRecycleAdapter = new CalendarRecycleAdapter(showView.getContext(),CollectEventByDate(date));
                recyclerView.setAdapter(calendarRecycleAdapter);
                calendarRecycleAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();

                return true;

            }


        });

    }

    private ArrayList<CalendarEvents> CollectEventByDate(String date){
        ArrayList<CalendarEvents> arrayList = new ArrayList<>();
        calendarDbOpenHelper = new CalendarDBOpenHelper(context);
        SQLiteDatabase database = calendarDbOpenHelper.getReadableDatabase();
        Cursor cursor = calendarDbOpenHelper.ReadEvents(date,database);
        while (cursor.moveToNext()){

            String event = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.EVENT));
            String time = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.YEAR));

            CalendarEvents calendarEvents = new CalendarEvents(event,time,Date,month,Year);
            arrayList.add(calendarEvents);


        }
        cursor.close();
        calendarDbOpenHelper.close();
        return arrayList;
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //일정 DB에 저장
    private void SaveEvent(String event, String time, String date, String month, String year){

        calendarDbOpenHelper = new CalendarDBOpenHelper(context);
        SQLiteDatabase database = calendarDbOpenHelper.getWritableDatabase();
        calendarDbOpenHelper.SaveEvent(event, time,date,month,year,database);
        calendarDbOpenHelper.close();
        Toast.makeText(context, "일정이 저장 되었습니다", Toast.LENGTH_SHORT).show();

    }


    private  void  IntializeLayout() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout,this);
        NextButton = (ImageButton)view.findViewById(R.id.nextBtn);
        PreviousButton =(ImageButton) view.findViewById(R.id.previonsBtn);
        CurrentDate = (TextView) view.findViewById(R.id.current_Date);
        gridView =(GridView) view.findViewById(R.id.gridview);
    }

    //달력 요일별로 맞추기
    private  void SetUpCalendar(){
        String currwntDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currwntDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);

        }

        calendarGridAdapter = new CalendarGridAdapter(context,dates,calendar, calendarEventsList);
        gridView.setAdapter(calendarGridAdapter);

    }

    private void CollectEventsPerMonth(String Month, String year){
        calendarEventsList.clear();
        calendarDbOpenHelper = new CalendarDBOpenHelper(context);
        SQLiteDatabase database = calendarDbOpenHelper.getReadableDatabase();
        Cursor cursor = calendarDbOpenHelper.ReadEventsperMonth(Month,year,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.EVENT));
            String time = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.TIME));
            String date = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(CalendarDBStructure.YEAR));
            CalendarEvents calendarEvents = new CalendarEvents(event,time,date,month,Year);
            calendarEventsList.add(calendarEvents);

        }
        cursor.close();
        calendarDbOpenHelper.close();

    }

}
