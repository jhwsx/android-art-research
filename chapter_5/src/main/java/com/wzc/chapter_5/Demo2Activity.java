package com.wzc.chapter_5;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.RemoteViews;

public class Demo2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
    }


    public void send_broadcast(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        remoteViews.setImageViewResource(R.id.iv, R.drawable.icon1);
        remoteViews.setTextViewText(R.id.tv_title, "msg from process: " + Process.myPid());
        PendingIntent openDemo1PendingIntent = PendingIntent.getActivity(Demo2Activity.this, 0,
                new Intent(Demo2Activity.this, Demo1Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.rl, openDemo1PendingIntent);
        PendingIntent openDemo2PendingIntent = PendingIntent.getActivity(Demo2Activity.this, 0,
                new Intent(Demo2Activity.this, Demo2Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openDemo2PendingIntent);
        Intent intent = new Intent(Constants.REMOTE_ACTION);
        intent.putExtra(Constants.EXTRA_REMOTEVIEWS, remoteViews);
        sendBroadcast(intent);
    }

}
