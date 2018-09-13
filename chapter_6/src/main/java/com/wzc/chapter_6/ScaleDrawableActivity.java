package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/9/14.
 */

public class ScaleDrawableActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, ScaleDrawableActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_drawable);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ScaleDrawable scaleDrawable = (ScaleDrawable) ll.getBackground();
        scaleDrawable.setLevel(1);
    }
}
