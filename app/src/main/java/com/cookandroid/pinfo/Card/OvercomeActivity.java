package com.cookandroid.pinfo.Card;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.pinfo.R;

public class OvercomeActivity extends AppCompatActivity {
    private EditText over_title;
    private EditText over_contents;
    private long OverId = -1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overcome_write);
        setTitle("PInfo 의약품 정보 알리미");

        over_title = findViewById(R.id.over_title);
        over_contents = findViewById(R.id.over_contents);

        Intent intent = getIntent();
        if (intent != null) {
            OverId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String contents = intent.getStringExtra("contents");

            over_title.setText(title);
            over_contents.setText(contents);
        }
    }

    @Override
    public void onBackPressed() {
        String title = over_title.getText().toString();
        String contents = over_contents.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(OvercomeContract.OvercomeEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(OvercomeContract.OvercomeEntry.COLUMN_NAME_CONTENTS, contents);

        SQLiteDatabase db = OvercomeDb.getsInstance(this).getWritableDatabase();
        if (OverId == -1) {
            long newRowId = db.insert(OvercomeContract.OvercomeEntry.TABLE_NAME, null, contentValues);

            if (newRowId == -1 ) {
                Toast.makeText(this, "저장에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "글이 저장되었습니다", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        } else {
            int count = db.update(OvercomeContract.OvercomeEntry.TABLE_NAME, contentValues, OvercomeContract.OvercomeEntry._ID + " = " + OverId, null);
            if (count == 0) {
                Toast.makeText(this, "수정에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "글이 수정되었습니다", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }

        super.onBackPressed();
    }
}
