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

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Spinner mSpinner;
    private int flag = PendingIntent.FLAG_ONE_SHOT;
    private EditText mEtId;
    private EditText mEtRequestCode;
    private EditText mEtExtraNum;
    private MyReceiver mMyReceiver;
    private FrameLayout mFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtId = (EditText) findViewById(R.id.et_id);
        mEtRequestCode = (EditText) findViewById(R.id.et_request_code);
        mEtExtraNum = (EditText) findViewById(R.id.et_extra_num);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setSelection(0, true);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    flag = PendingIntent.FLAG_ONE_SHOT;
                } else if (position == 1) {
                    flag = PendingIntent.FLAG_CANCEL_CURRENT;
                } else if (position == 2) {
                    flag = PendingIntent.FLAG_UPDATE_CURRENT;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mFl = (FrameLayout) findViewById(R.id.fl);
        IntentFilter intentFilter = new IntentFilter(Constants.REMOTE_ACTION);
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver, intentFilter);
    }

    public void standard_notification(View view) {
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
        Log.d(TAG, "send intent = " + intent.hashCode());
        intent.putExtra(Demo1Activity.EXTRA_TEXT, "text " + mEtExtraNum.getText().toString());
        String requestCodeStr = mEtRequestCode.getText().toString();
        int requestCode = Integer.parseInt(TextUtils.isEmpty(requestCodeStr) ? "0" : requestCodeStr);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                requestCode, intent, flag);
        notification.setLatestEventInfo(MainActivity.this,
                "chapter_5", "this is notification " + mEtExtraNum.getText().toString(), pendingIntent);
        String string = mEtId.getText().toString();
        string = TextUtils.isEmpty(string) ? "0" : string;
        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Integer.parseInt(string), notification);

    }

    int count;
    public void custom_notification(View view) {
        count++;
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "hello world";
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.tv_title, "chapter_5 "+count);
        remoteViews.setImageViewResource(R.id.iv, R.drawable.icon);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(MainActivity.this, 2,
                new Intent(MainActivity.this, Demo2Activity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(count, notification);
    }

    public void simulated_notification(View view) {
        Intent intent = new Intent(MainActivity.this, Demo2Activity.class);
        startActivity(intent);
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
                View view = remoteViews.apply(context, mFl);
                mFl.addView(view);
            }
        }
    }

}
