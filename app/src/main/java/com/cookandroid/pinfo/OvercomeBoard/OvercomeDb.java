package com.cookandroid.pinfo.OvercomeBoard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OvercomeDb extends SQLiteOpenHelper {
    private static OvercomeDb sInstance; // 인스턴스 선언

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "OvercomeContract.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT, %s TEXT)",
                    OvercomeContract.OvercomeEntry.TABLE_NAME,
                    OvercomeContract.OvercomeEntry._ID,
                    OvercomeContract.OvercomeEntry.COLUMN_NAME_TITLE,
                    OvercomeContract.OvercomeEntry.COLUMN_NAME_CONTENTS);

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + OvercomeContract.OvercomeEntry.TABLE_NAME;

    public static OvercomeDb getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OvercomeDb(context);
        }
        return sInstance;
    }

    private OvercomeDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    // 이전 테이블과 현재 테이블의 변경된 부분 처리
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
