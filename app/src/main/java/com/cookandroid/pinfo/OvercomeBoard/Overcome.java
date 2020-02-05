package com.cookandroid.pinfo.OvercomeBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.pinfo.R;

public class Overcome extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT = 1000;
    private OverAdapter oAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overcome_main);
        setTitle("PInfo 의약품 정보 알리미");

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OvercomeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_INSERT);
            }
        });

        ListView listview = findViewById(R.id.overcome_list);

        Cursor cursor = getOvercomeCursor();
        oAdapter = new OverAdapter(this, cursor);
        listview.setAdapter(oAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(Overcome.this, OvercomeActivity.class);

                Cursor cursor = (Cursor) oAdapter.getItem(i);

                String title = cursor.getString(cursor.getColumnIndexOrThrow(OvercomeContract.OvercomeEntry.COLUMN_NAME_TITLE));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(OvercomeContract.OvercomeEntry.COLUMN_NAME_CONTENTS));

                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);

                // 수정된 마지막 데이터 보냄
                startActivityForResult(intent, REQUEST_CODE_INSERT);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                final long deleteId = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(Overcome.this);
                builder.setTitle("글 삭제");
                builder.setMessage("글을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = OvercomeDb.getsInstance(Overcome.this).getWritableDatabase();
                        int deleteCount = db.delete(OvercomeContract.OvercomeEntry.TABLE_NAME, OvercomeContract.OvercomeEntry._ID + " = " + deleteId, null);
                        if (deleteCount == 0) {
                            Toast.makeText(Overcome.this, "삭제에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
                        } else {
                           oAdapter.swapCursor(getOvercomeCursor());
                           Toast.makeText(Overcome.this, "글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });
    }

    private Cursor getOvercomeCursor() {
        OvercomeDb overcomeDb = OvercomeDb.getsInstance(this);
        return overcomeDb.getReadableDatabase().
                query(OvercomeContract.OvercomeEntry.TABLE_NAME,
                        null, null, null, null, null, OvercomeContract.OvercomeEntry._ID + " DESC");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) { // 데이터 갱신
            oAdapter.swapCursor(getOvercomeCursor());
        }
    }

    private static class OverAdapter extends CursorAdapter {

        public OverAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1
                            , viewGroup, false);
        }

        // 데이터를 실제로 전달하는 부분
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView title = view.findViewById(android.R.id.text1);
            title.setText(cursor.getString(cursor.getColumnIndexOrThrow(OvercomeContract.OvercomeEntry.COLUMN_NAME_TITLE)));
        }
    }
}
