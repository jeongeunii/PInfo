package com.cookandroid.pinfo.LMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cookandroid.pinfo.OvercomeBoard.ManagerActivity;
import com.cookandroid.pinfo.OvercomeBoard.Overcome;
import com.cookandroid.pinfo.OvercomeBoard.QuestionActivity;
import com.cookandroid.pinfo.R;

public class BoardActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_board);
        setTitle("PInfo 의약품 정보 알리미");

        Button b_lb_over = (Button)findViewById(R.id.b_lb_over);
        Button b_lb_ques = (Button)findViewById(R.id.b_lb_ques);
        Button b_lb_mana = (Button)findViewById(R.id.b_lb_mana);

        b_lb_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Overcome.class);
                startActivity(intent);
            }
        });

        b_lb_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                startActivity(intent);
            }
        });

        b_lb_mana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}
