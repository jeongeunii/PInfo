package com.cookandroid.pinfo.Mypage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("리스브 작동중.", "Yay!");

        // fetch extra strings from the intent
        // tells the app whether the user pressed the alarm on button or the alarm off button
        String get_your_string = intent.getExtras().getString("extra");

        Log.e("What is the key? ", get_your_string);


        //스피너 선택시 나오는 값
        Integer get_your_whale_choice = intent.getExtras().getInt("whale_choice");

        Log.e("The whale choice is ", get_your_whale_choice.toString());

        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("extra", get_your_string);

        service_intent.putExtra("whale_choice", get_your_whale_choice);

        // ringtone서비스 시작
        context.startService(service_intent);

    }

}
