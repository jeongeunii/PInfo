package com.cookandroid.pinfo.Mypage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cookandroid.pinfo.R;

import java.util.ArrayList;

public class CalendarRecycleAdapter extends RecyclerView.Adapter<CalendarRecycleAdapter.MyViewHoler>{

    //Event가 일어났을때 Recycle을 Adater로 해주는 Class
    Context context;
    ArrayList<CalendarEvents> arrayList ;

    public CalendarRecycleAdapter(Context context, ArrayList<CalendarEvents> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowcalendar,parent,false
        );
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {

        CalendarEvents calendarEvents = arrayList.get(position);
        holder.Event.setText(calendarEvents.getEVENT());
        holder.DateTxt.setText(calendarEvents.getDATE());
        holder.Time.setText(calendarEvents.getTIME());

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{

        TextView DateTxt, Event, Time;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            DateTxt = (TextView)itemView.findViewById(R.id.eventdate);
            Event = (TextView)itemView.findViewById(R.id.eventname);
            Time = (TextView)itemView.findViewById(R.id.eventtime);

        }
    }
}