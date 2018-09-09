package com.wzc.chapter_5;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * @author wzc
 * @date 2018/8/31
 */
public class BlogActivity extends Activity implements View.OnClickListener {

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity);
        Button btnShowNotification = (Button) findViewById(R.id.btn_show_notification);
        Button btnUpdateNotification = (Button) findViewById(R.id.btn_update_notification);
        Button btnCancelNotification = (Button) findViewById(R.id.btn_cancel_notification);
        btnShowNotification.setOnClickListener(this);
        btnUpdateNotification.setOnClickListener(this);
        btnCancelNotification.setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_show_notification) {
            showNotification("This is a title");
        } else if (id == R.id.btn_update_notification) {
            showNotification("This is a updated title");
        } else if (id == R.id.btn_cancel_notification) {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
    }

    private void showNotification(String title) {
        Notification notification = new Notification();
        notification.icon = R.drawable.icon1;
        notification.tickerText = "notification is coming";
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_title, title);
        remoteViews.setImageViewResource(R.id.image_view, R.drawable.icon1);
        notification.contentView = remoteViews;
        PendingIntent pendingIntent = PendingIntent.getActivity(BlogActivity.this, 0,
                new Intent(BlogActivity.this, DemoAActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = pendingIntent;
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }
}
