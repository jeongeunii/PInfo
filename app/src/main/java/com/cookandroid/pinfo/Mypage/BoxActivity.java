package com.cookandroid.pinfo.Mypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cookandroid.pinfo.LMain.Pill;
import com.cookandroid.pinfo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BoxActivity extends AppCompatActivity {

    ListView listView;
    List<String> listBox;
    ArrayAdapter<String> adapter;
    ArrayList<Pill> pillBoxList = new ArrayList<>();
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_box);
        setTitle("PInfo 의약품 정보 알리미");

        listView = (ListView)findViewById(R.id.ListBox);
        listBox = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listBox);
        listView.setAdapter(adapter);

        pillBoxList = getPillsItems();
        if (pillBoxList != null) {
            for(int i=0; i<pillBoxList.size(); i++) {
                listBox.add(pillBoxList.get(i).toString());
            }
        }

        btnReset = (Button)findViewById(R.id.BtnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                setPillsItems(null);
            }
        });
    }

    public ArrayList<Pill> getPillsItems() {
        String json = this
                .getSharedPreferences("pinfo", Context.MODE_PRIVATE)
                .getString("pills", "");

        if(json == "") {
            // 저장된 값이 없으면 새로운 리스트를 만들어서 리턴
            return new ArrayList<Pill>();
        }

        Type type = new TypeToken<ArrayList<Pill>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public void setPillsItems(ArrayList<Pill> items) {
        this.getSharedPreferences("pinfo", Context.MODE_PRIVATE)
                .edit()
                .putString("pills", new Gson().toJson(items).toString())
                .apply();
    }
}
