package com.jcisme.clock.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;

public class Clock extends BroadcastReceiver {

    private TextView mTextView = null;

    //TODO - This is hateful. Remove it.
    public Clock(TextView textView) {
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
