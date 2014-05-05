package com.jcisme.clock.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    private int mCurrentHour;
    private int mCurrentMinute;
    private ImageView mImageViewHourHand;
    private ImageView mImageViewMinuteHand;
    private TimeBroadcastReceiver mTimeBroadcastReceiver = new TimeBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageViewHourHand = (ImageView) findViewById(R.id.imageViewHourHand);
        mImageViewMinuteHand = (ImageView) findViewById(R.id.imageViewMinuteHand);
        mCurrentHour = 0;
        mCurrentMinute = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mTimeBroadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        updateDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTimeBroadcastReceiver);
    }

    protected void updateDisplay() {
        advanceMinuteHand();
//        advanceHourHand(newHour);
    }

    protected void advanceHourHand() {
        Calendar dateTime = Calendar.getInstance();
        int newHour =  dateTime.get(Calendar.HOUR);
        newHour = newHour  % 12;
        if (newHour == mCurrentHour) return;

        float startAngle = mCurrentHour * 30f;
        float endAngle = (mCurrentHour + 1) * 30f;
        RotateAnimation moveHourHandAnimation = new RotateAnimation(startAngle, endAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        moveHourHandAnimation.setDuration(100);
        moveHourHandAnimation.setFillAfter(true);
        moveHourHandAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //playHourUpdateSound();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        mImageViewHourHand.setAnimation(moveHourHandAnimation);

        mCurrentHour = (mCurrentHour + 1) % 12;
    }

    private void playHourUpdateSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.clong);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    protected void advanceMinuteHand() {
        Calendar dateTime = Calendar.getInstance();
        int newMinute =  dateTime.get(Calendar.MINUTE);
        newMinute = (60 + newMinute) % 60;
        if (newMinute == mCurrentMinute) return;

        float startAngle = mCurrentMinute * 6f;
        float endAngle = (mCurrentMinute + 1) * 6f;
        RotateAnimation moveMinuteHandAnimation = new RotateAnimation(startAngle, endAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        moveMinuteHandAnimation.setDuration(100);
        moveMinuteHandAnimation.setFillAfter(true);
        moveMinuteHandAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playUpdateMinuteSound();
                advanceMinuteHand();
             }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        mImageViewMinuteHand.setAnimation(moveMinuteHandAnimation);

        mCurrentMinute = (mCurrentMinute + 1) % 60;
    }

    private void playUpdateMinuteSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mechanical_clonk);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    private class TimeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equalsIgnoreCase(intent.getAction())) {
                MainActivity.this.updateDisplay();
            }
        }
    }
}