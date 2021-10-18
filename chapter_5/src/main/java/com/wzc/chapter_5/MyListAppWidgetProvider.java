package com.wzc.chapter_5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * @author wangzhichao
 * @since 2021/10/19
 */
public class MyListAppWidgetProvider extends AppWidgetProvider {
    private static final String CLICK_ACTION = "com.wzc.chapter_5.click_list_action";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (CLICK_ACTION.equals(action)) {
            String text = intent.getStringExtra(Demo1Activity.EXTRA_TEXT);
            Toast.makeText(context, "click: " + text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        remoteViews.setRemoteAdapter(R.id.listview, new Intent(context, ListService.class));
        Intent clickIntent = new Intent();
        clickIntent.setAction(CLICK_ACTION);
        PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(
                context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listview, pendingIntentTemplate);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
