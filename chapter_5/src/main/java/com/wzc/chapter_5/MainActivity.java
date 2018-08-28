package com.wzc.chapter_5;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void standard_notification(View view) {
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(MainActivity.this,
                "chapter_5", "this is notification", pendingIntent);
        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);

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
