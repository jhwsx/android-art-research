package com.wzc.chapter_9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyStaticReceiver extends BroadcastReceiver {
    private static final String TAG = "MyStaticReceiver";
    public static final String ACTION_LAUNCH_STATIC = "com.wzc.receiver.LAUNCH_STATIC";
    private int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: receive action = " + action);
        i++;
        Log.d(TAG, "onReceive: this=" + this);
        Log.d(TAG, "onReceive: i=" + i);
        if (ACTION_LAUNCH_STATIC.equals(action)) {
            Log.d(TAG, "onReceive: do launch work...");
        }
    }
}
