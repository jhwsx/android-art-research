package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wzc on 2018/9/11.
 */

public class BitmapDrawableActivity extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, BitmapDrawableActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_drawable);
    }
}
