package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzc
 * @date 2018/8/11
 */
public class HorizontalScrollViewExActivity extends Activity {

    private HorizontalScrollViewEx mHorizontalScrollViewEx;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scrollview_ex);
        mHorizontalScrollViewEx = (HorizontalScrollViewEx) findViewById(R.id.container);

        final int screenWidth = getScreenMetrics(this).widthPixels;
        final int screenHeight = getScreenMetrics(this).heightPixels;
        mLayoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < 3; i++) {
            View view = mLayoutInflater.inflate(R.layout.layout_page, mHorizontalScrollViewEx, false);
            view.getLayoutParams().width = screenWidth;
            view.setBackgroundColor(createColor(i));
            TextView tv = (TextView) view.findViewById(R.id.tv_page);
            tv.setText("Page " + i);
            ListView listView = (ListView) view.findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, createList()));
            mHorizontalScrollViewEx.addView(view);
        }
    }

    private int createColor(int i) {
        switch (i) {
            case 0:
                return Color.parseColor("#44ff0000");
            case 1:
                return Color.parseColor("#4400ff00");
            case 2:
            default:
                return Color.parseColor("#440000ff");
        }
    }

    private List<String> createList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add("Item " + i);
        }
        return result;
    }

    private DisplayMetrics getScreenMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
