package com.wzc.chapter_5;

import static com.wzc.chapter_5.Demo1Activity.EXTRA_TEXT;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * @author wangzhichao
 * @since 2021/10/19
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "ListProvider";
    private final Context context;
    private final String[] array;

    public ListProvider(Context context) {
        this.context = context;
        array = context.getResources().getStringArray(R.array.demo_string_array);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: ");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: ");
        return array.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: position=" + position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, array[position]);
        Intent intent = new Intent(context, Demo2Activity.class);
        intent.putExtra(EXTRA_TEXT, array[position]);
        remoteViews.setOnClickFillInIntent(android.R.id.text1, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
