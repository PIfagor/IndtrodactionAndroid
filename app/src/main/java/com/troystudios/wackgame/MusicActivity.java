package com.troystudios.wackgame;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.ev;

import area730.com.first.R;

public class MusicActivity extends Activity {


    GestureDetector gestureDetector;
    SoundPool sp;
    int tapId;
    int doubleTapID;
    int backID;
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        sp.play(backID, 1, 1, 0, 0, 1);
        new ViewConfiguration().getLongPressTimeout();
        // MediaPlayer.create(getApplicationContext(), R.raw.crystal_ball).start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector =  new GestureDetector(getApplicationContext(), new GestureListener());
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        tapId = sp.load(getApplicationContext(), R.raw.tap, 1); // in 2nd param u have to pass your desire ringtone
        doubleTapID = sp.load(getApplicationContext(), R.raw.double_tap, 1); // in 2nd param u have to pass your desire ringtone
        backID = sp.load(getApplicationContext(), R.raw.crystal_ball, 1);
        // setContentView(R.layout.activity_music);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return gestureDetector.onTouchEvent(e);
    }



    private class GestureListener extends GestureDetector.SimpleOnGestureListener {


        public boolean onSingleTapConfirmed (MotionEvent ev) {
            sp.play(tapId, 1, 1, 0, 0, 1);
            return true;
        }



//        @Override
//        public boolean onDown(MotionEvent event) {
//         //   Log.d(DEBUG_TAG,"onDown: " + event.toString());
//            return true;
//        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
           sp.play(doubleTapID, 1, 1, 0, 0, 1);
            return true;
        }

    }
}
