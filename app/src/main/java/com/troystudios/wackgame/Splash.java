package com.troystudios.wackgame;

/**
 * Created by Wise on 04.02.2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import area730.com.first.R;

public class Splash extends MusicActivity  {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 15000;
    private   MediaPlayer mediaPlayer ;
    private  long  timer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
       // setContentView(R.layout.splash);
        //setOnTouchListener(this);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
        setContentView(R.layout.splash);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fon_music);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    //public boolean onTouch(View view, MotionEvent event)
    public boolean onTouchEvent(MotionEvent e){
        super.onTouchEvent(e);


        if(e.getAction() == 0){
            timer = System.currentTimeMillis();

        }
        else if (e.getAction() == 1) {
            long timer2 = System.currentTimeMillis();
            if (timer2 - timer < 1500)
            {
                Toast.makeText(getApplicationContext(), "Hold finger more",
                        Toast.LENGTH_SHORT ).show();
            }
            else
            {
                Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }

        }

        // событие
//        int actionMask = event.getActionMasked();
//        // индекс касания
//        int pointerIndex = event.getActionIndex();
//        // число касаний
//         pointerCount = event.getPointerCount();
//
//        switch (actionMask) {
//            case MotionEvent.ACTION_DOWN: // первое касание
//                //countFingers++;
//                makeEvent();
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN: // последующие касания
//               // countFingers++;
//                makeEvent();
//                break;
//
//            case MotionEvent.ACTION_UP: // прерывание последнего касания
//                //countFingers--;
//                makeEvent();
//            case MotionEvent.ACTION_POINTER_UP: // прерывания касаний
//               // countFingers--;
//                makeEvent();
//                break;
//        }
        return true;
    }




    private void makeEvent() {
        switch (pointerCount)   {
            case 1:
                break;
            case 2:
                setContentView(R.layout.splash);
                break;
            case 3:
                Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
                break;
        }

    }

    //private boolean inTouch = false;
    private   int pointerCount;



}
