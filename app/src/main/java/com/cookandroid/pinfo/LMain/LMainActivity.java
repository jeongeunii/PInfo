package com.cookandroid.pinfo.LMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.cookandroid.pinfo.R;

public class LMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain);
        setTitle("PInfo 의약품 정보 알리미");

        LinearLayout b_l_mypage = (LinearLayout) findViewById(R.id.b_l_mypage);
        LinearLayout b_l_pill = (LinearLayout) findViewById(R.id.b_l_pill);
        LinearLayout b_l_store = (LinearLayout) findViewById(R.id.b_l_store);
        LinearLayout b_l_mother = (LinearLayout) findViewById(R.id.b_l_mother);
        LinearLayout b_l_board = (LinearLayout) findViewById(R.id.b_l_board);
        LinearLayout b_l_qr = (LinearLayout) findViewById(R.id.b_l_qr);
        LinearLayout b_l_map = (LinearLayout) findViewById(R.id.b_l_map);

        b_l_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });

        b_l_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PillActivity.class);
                startActivity(intent);
            }
        });

        b_l_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        b_l_mother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MotherActivity.class);
                startActivity(intent);
            }
        });

        b_l_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        b_l_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QrActivity.class);
                startActivity(intent);
            }
        });

        b_l_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

    }
}