package com.cookandroid.pinfo.OvercomeBoard;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cookandroid.pinfo.R;

/**
 * Created by Superman on 2019-11-08.
 */

public class QuestionActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setTitle("PInfo 의약품 정보 알리미");
    }
}
