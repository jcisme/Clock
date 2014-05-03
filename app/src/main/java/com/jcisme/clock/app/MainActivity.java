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

    private TimeBroadcastReceiver clock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView textView = (TextView) findViewById(R.id.textView);
        clock = new TimeBroadcastReceiver(textView);
        //TODO - Determine how to get new Handler.Callback to work with receiver
        registerReceiver(clock, new IntentFilter(Intent.ACTION_TIME_TICK));
        clock.updateDisplayedTime();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(clock);
        clock = null;
    }

    private class TimeBroadcastReceiver extends BroadcastReceiver {

        private TextView mTextView = null;

        //TODO - This is hateful. Remove it.
        public TimeBroadcastReceiver(TextView textView) {
            mTextView = textView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateDisplayedTime();
        }

        // TODO - Determine if this should be here or in MainActivity (i.e. MVC)
        public void updateDisplayedTime() {
            Date date = new Date();
            CharSequence time = DateFormat.format("hh:mm a", date.getTime());
            time = time.toString().toUpperCase();
            mTextView.setText(time);
        }
    }
}
