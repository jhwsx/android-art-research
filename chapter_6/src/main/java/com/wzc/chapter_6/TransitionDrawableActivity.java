package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/9/14.
 */

public class TransitionDrawableActivity extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, TransitionDrawableActivity.class);
        context.startActivity(starter);
    }

    boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_drawable);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable drawable = (TransitionDrawable) ll.getBackground();
                if (start) {
                    drawable.reverseTransition(500);
                } else {
                    drawable.startTransition(500);
                }
                start = !start;
            }
        });
    }
}
