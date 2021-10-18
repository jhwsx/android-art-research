package com.wzc.chapter_5;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Spinner;
// A
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyReceiver mMyReceiver;
    private FrameLayout mFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFl = (FrameLayout) findViewById(R.id.fl);
        IntentFilter intentFilter = new IntentFilter(Constants.REMOTE_ACTION);
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        if (mMyReceiver != null) {
            unregisterReceiver(mMyReceiver);
            mMyReceiver = null;
        }
        super.onDestroy();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.REMOTE_ACTION.equals(intent.getAction())) {
                RemoteViews remoteViews = intent.getParcelableExtra(Constants.EXTRA_REMOTEVIEWS);
                updateUI(context, remoteViews);
            }
        }

        private void updateUI(Context context, RemoteViews remoteViews) {
            int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
            View view = getLayoutInflater().inflate(layoutId, mFl, false);
            remoteViews.reapply(context, view);
            mFl.addView(view);
        }
    }



}
