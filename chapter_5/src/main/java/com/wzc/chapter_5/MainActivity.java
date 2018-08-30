package com.wzc.chapter_5;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Spinner mSpinner;
    private int flag = PendingIntent.FLAG_ONE_SHOT;
    private EditText mEtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtId = (EditText) findViewById(R.id.et_id);
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
    }

    public void standard_notification(View view) {
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
        Log.d(TAG, "send intent = " + intent.hashCode());
        String string = mEtId.getText().toString();
        string = TextUtils.isEmpty(string) ? "0" : string;
        intent.putExtra(Demo1Activity.EXTRA_TEXT, "text " + string);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                0, intent, flag);
        notification.setLatestEventInfo(MainActivity.this,
                "chapter_5", "this is notification " + string, pendingIntent);
        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Integer.parseInt(string),notification);

    }

    public void custom_notification(View view) {
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "hello world";
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.tv_title, "chapter_5");
        remoteViews.setImageViewResource(R.id.iv, R.drawable.icon);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(MainActivity.this, 2,
                new Intent(MainActivity.this, Demo2Activity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2,notification);
    }
}
