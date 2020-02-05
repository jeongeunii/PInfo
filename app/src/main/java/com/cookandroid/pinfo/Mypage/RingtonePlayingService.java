package com.cookandroid.pinfo.Mypage;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cookandroid.pinfo.R;

import java.util.Random;

public class RingtonePlayingService extends Service  //Service
{


    MediaPlayer media_song;
    int startId;
    boolean isRunning;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);


        String state = intent.getExtras().getString("extra");

        Integer whale_sound_choice = intent.getExtras().getInt("whale_choice");

        Log.e("Ringtone extra is ", state);
        Log.e("Whale choice is ", whale_sound_choice.toString());



        NotificationManager notify_manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), AlarmActivity.class);

        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        //pedingInt로 알람 울릴시 스택으로 쌓으면서 해당 클래스로 이동 -> 약 체크 클래스
        Intent checkIntent = new Intent(this, CheckActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CheckActivity.class);
        stackBuilder.addNextIntent(checkIntent);

        //약체크로 넘어가기
        PendingIntent checkPedingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = null;



        // notification
        //   Notification notification_popup = new Notification.Builder(this)
      /*  builder.setAutoCancel(true)
                .setContentTitle("알람 울리고 있어요")
                .setContentText("여기를 누르세요")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(checkPedingIntent)
                .setAutoCancel(true)
                .build(); */





        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }


        //알람 실행 버튼을 클릭했을때
        //음악 실행 될때
        if (!this.isRunning && startId == 1) {
            Log.e("there is no music, ", "and you want start");

            this.isRunning = true;
            this.startId = 0;

            //26버전 이상일때 채널을 하나 생성한다
            if (Build.VERSION.SDK_INT >= 26){
                Log.e("26버전 이상","오레오");
                NotificationChannel mChannel = new NotificationChannel("andokcapp", "andokdcapp",notify_manager.IMPORTANCE_DEFAULT);
                notify_manager.createNotificationChannel(mChannel);
                builder = new NotificationCompat.Builder(this, mChannel.getId());


            }
            else {
                builder = new NotificationCompat.Builder(this); }


            builder.setAutoCancel(true)
                    .setContentTitle("알람 울리고 있어요")
                    .setContentText("여기를 누르세요")
                    .setSmallIcon(R.drawable.ic_action_call)
                    .setContentIntent(checkPedingIntent)
                    .setAutoCancel(true)
                    .build();

            notify_manager.notify(0, builder.build());





            if (whale_sound_choice == 0) {
                //노래 랜덤으로 재생

                int minimum_number = 1;
                int maximum_number = 6; //원하는 노래 갯수

                Random random_number = new Random();
                int whale_number = random_number.nextInt(maximum_number + minimum_number);
                Log.e("random number is " , String.valueOf(whale_number));


                if (whale_number == 1) {
                    media_song = MediaPlayer.create(this, R.raw.ok);
                    media_song.start();
                }
                else if (whale_number == 2) {
                    // 알람 생성
                    media_song = MediaPlayer.create(this, R.raw.powerup);
                    // 알람 시작
                    media_song.start();
                }
                else if (whale_number == 3) {
                    media_song = MediaPlayer.create(this, R.raw.apink);
                    media_song.start();
                }
                else if (whale_number == 4) {
                    media_song = MediaPlayer.create(this, R.raw.redplant);
                    media_song.start();
                }
                else if (whale_number == 5) {
                    media_song = MediaPlayer.create(this, R.raw.rebye);
                    media_song.start();
                }
                else if (whale_number == 6) {
                    media_song = MediaPlayer.create(this, R.raw.twice);
                    media_song.start();
                }
                /*else if (whale_number == 7) {
                    media_song = MediaPlayer.create(this, R.raw.humpback_whale_song);
                    media_song.start();
                }
                else if (whale_number == 8) {
                    media_song = MediaPlayer.create(this, R.raw.humpback_whale_song_with_outboard_engine_noise);
                    media_song.start();
                }
                else if (whale_number == 9) {
                    media_song = MediaPlayer.create(this, R.raw.humpback_wheeze_blows);
                    media_song.start();
                }
                else if (whale_number == 10) {
                    media_song = MediaPlayer.create(this, R.raw.killerwhale_echolocation_clicks);
                    media_song.start();
                }
                else if (whale_number == 11) {
                    media_song = MediaPlayer.create(this, R.raw.killerwhale_offshore);
                    media_song.start();
                }
                else if (whale_number == 12) {
                    media_song = MediaPlayer.create(this, R.raw.killerwhale_resident);
                    media_song.start();
                }
                else if (whale_number == 13) {
                    media_song = MediaPlayer.create(this, R.raw.killerwhale_transient);
                    media_song.start();
                } */
                else {
                    media_song = MediaPlayer.create(this, R.raw.twice);
                    media_song.start();
                }


            }
            else if (whale_sound_choice == 1) {
                // create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.ok);
                // start the ringtone
                media_song.start();
            }
            else if (whale_sound_choice == 2) {
                // create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.powerup);
                // start the ringtone
                media_song.start();
            }
            else if (whale_sound_choice == 3) {
                media_song = MediaPlayer.create(this, R.raw.apink);
                media_song.start();
            }
            else if (whale_sound_choice == 4) {
                media_song = MediaPlayer.create(this, R.raw.redplant);
                media_song.start();
            }
            else if (whale_sound_choice == 5) {
                media_song = MediaPlayer.create(this, R.raw.rebye);
                media_song.start();
            }
            else if (whale_sound_choice == 6) {
                media_song = MediaPlayer.create(this, R.raw.twice);
                media_song.start();
            }/*

            else if (whale_sound_choice == 7) {
                media_song = MediaPlayer.create(this, R.raw.humpback_whale_song);
                media_song.start();
            }
            else if (whale_sound_choice == 8) {
                media_song = MediaPlayer.create(this, R.raw.humpback_whale_song_with_outboard_engine_noise);
                media_song.start();
            }
            else if (whale_sound_choice == 9) {
                media_song = MediaPlayer.create(this, R.raw.humpback_wheeze_blows);
                media_song.start();
            }
            else if (whale_sound_choice == 10) {
                media_song = MediaPlayer.create(this, R.raw.killerwhale_echolocation_clicks);
                media_song.start();
            }
            else if (whale_sound_choice == 11) {
                media_song = MediaPlayer.create(this, R.raw.killerwhale_offshore);
                media_song.start();
            }
            else if (whale_sound_choice == 12) {
                media_song = MediaPlayer.create(this, R.raw.killerwhale_resident);
                media_song.start();
            }
            else if (whale_sound_choice == 13) {
                media_song = MediaPlayer.create(this, R.raw.killerwhale_transient);
                media_song.start();
            }*/
            else {
                media_song = MediaPlayer.create(this, R.raw.ok);
                media_song.start();
            }







        }

        //알람 종료 버튼을 눌렀을때 알람 꺼지게 하기
        else if (this.isRunning && startId == 0) {
            Log.e("there is music, ", "and you want end");

            // 작동 멈춤 ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        //알람 종료를 눌렀을때 실패 한 경우
        else if (!this.isRunning && startId == 0) {
            Log.e("there is no music, ", "and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        //알람 시작을 눌렀는대 시작을 안하는 경우
        else if (this.isRunning && startId == 1) {
            Log.e("there is music, ", "and you want start");

            this.isRunning = true;
            this.startId = 1;

        }


        else {
            Log.e("else ", "somehow you reached this");

        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }



}