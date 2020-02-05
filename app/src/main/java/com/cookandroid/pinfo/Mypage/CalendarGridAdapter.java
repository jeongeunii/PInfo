package com.cookandroid.pinfo.Mypage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cookandroid.pinfo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<CalendarEvents> events;
    LayoutInflater inflater;



    public CalendarGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<CalendarEvents> events) {
        super(context,R.layout.single_cell_calendar);

        this.dates= dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);


    }
    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        //화면에 출력 되는 달
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        //화면에 출력 되는 년도
        int displayYear = dateCalendar.get(Calendar.YEAR);
        //실제달
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        //실제년도
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.single_cell_calendar,parent,false);
        }

        //이번달 배경 색상
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        else {
            //전월달 배경 색상
            view.setBackgroundColor(Color.parseColor("#EEEEEE"));
            view.setVisibility(View.GONE); //전월달 숨김
        }


        TextView Day_Number = (TextView)view.findViewById(R.id.calendar_day);
        TextView EventNumber =(TextView) view.findViewById(R.id.events_id);
        Day_Number.setText(String.valueOf(DayNo));
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i< events.size(); i++){
            eventCalendar.setTime(ConvertStringToDate(events.get(i).getDATE()));
            if (DayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                arrayList.add(events.get(i).getEVENT());
                EventNumber.setText(arrayList.size()+"개 일정");
                EventNumber.setTextColor(Color.parseColor("#ff0000"));


            }

        }


        return  view;
    }

    private  Date ConvertStringToDate (String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date date = null;
        try {
            date = format.parse(eventDate);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        return  date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}