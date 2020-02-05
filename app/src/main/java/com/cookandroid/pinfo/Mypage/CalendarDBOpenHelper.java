package com.cookandroid.pinfo.Mypage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class CalendarDBOpenHelper extends SQLiteOpenHelper {

    //table 생성 키
    private static final String CREATE_EVENTS_TABLE= "create table " + CalendarDBStructure.EVENT_TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CalendarDBStructure.EVENT+" TEXT,"
            + CalendarDBStructure.TIME+" TEXT,"
            + CalendarDBStructure.DATE+" TEXT,"
            + CalendarDBStructure.MONTH+" TEXT,"
            + CalendarDBStructure.YEAR+" TEXT)";

    private static final String DROP_EVENTS_TABLE=" DROP TABLE IF EXISTS " + CalendarDBStructure.EVENT_TABLE_NAME;


    public CalendarDBOpenHelper(@Nullable Context context) {
        super(context, CalendarDBStructure.DB_NAME, null, CalendarDBStructure.DB_VERSION);
    }



    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }


    //db내용을 저장 하는 메소드
    public void SaveEvent(String event, String time, String date, String month, String year, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarDBStructure.EVENT,event);
        contentValues.put(CalendarDBStructure.TIME,time);
        contentValues.put(CalendarDBStructure.DATE, date);
        contentValues.put(CalendarDBStructure.MONTH,month);
        contentValues.put(CalendarDBStructure.YEAR, year);
        database.insert(CalendarDBStructure.EVENT_TABLE_NAME, null,contentValues);

    }

    //db저장 읽기
    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {CalendarDBStructure.EVENT, CalendarDBStructure.TIME, CalendarDBStructure.DATE, CalendarDBStructure.MONTH, CalendarDBStructure.YEAR};
        String Selection = CalendarDBStructure.DATE+"=?";
        String [] SelectionArgs = {date};
        return database.query(CalendarDBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);

    }
    public Cursor ReadEventsperMonth(String month, String year, SQLiteDatabase database){
        String [] Projections = {CalendarDBStructure.EVENT, CalendarDBStructure.TIME, CalendarDBStructure.DATE, CalendarDBStructure.MONTH, CalendarDBStructure.YEAR};
        String Selection = CalendarDBStructure.MONTH +"=? and "+ CalendarDBStructure.YEAR+"=?";
        String [] SelectionArgs = {month,year};
        return database.query(CalendarDBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);

    }



}