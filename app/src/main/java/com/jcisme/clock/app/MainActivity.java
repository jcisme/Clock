package com.jcisme.clock.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private TextView mTextView;
    private TimeBroadcastReceiver mTimeBroadcastReceiver = new TimeBroadcastReceiver();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.textView);
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
        Date date = new Date();
        CharSequence time = DateFormat.format("hh:mm a", date.getTime());
        time = time.toString().toUpperCase();
        mTextView.setText(time);
    }

    private class TimeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equalsIgnoreCase(intent.getAction())) {
                updateDisplay();
            }
        }
    }
}