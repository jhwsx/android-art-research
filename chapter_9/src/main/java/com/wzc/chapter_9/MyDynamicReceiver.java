package com.wzc.chapter_9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDynamicReceiver extends BroadcastReceiver {
    private static final String TAG = "MyDynamicReceiver";
    public static final String ACTION_LAUNCH_DYNAMIC = "com.wzc.receiver.LAUNCH_DYNAMIC";
    public int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: receive action = " + action);
        i++;
        Log.d(TAG, "onReceive: this=" + this);
        Log.d(TAG, "onReceive: i = " + i);
        if (ACTION_LAUNCH_DYNAMIC.equals(action)) {
            Log.d(TAG, "onReceive: do launch work...");
        }
    }
}
