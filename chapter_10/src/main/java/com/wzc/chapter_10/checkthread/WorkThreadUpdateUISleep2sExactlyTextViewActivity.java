package com.wzc.chapter_10.checkthread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.wzc.chapter_10.R;

public class WorkThreadUpdateUISleep2sExactlyTextViewActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exactly_textview);
        tv = (TextView) findViewById(R.id.tv);
        new Thread(() -> {
            SystemClock.sleep(2000);
            tv.setText("I am text set in " + Thread.currentThread().getName());
        },"work-thread").start();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WorkThreadUpdateUISleep2sExactlyTextViewActivity.class);
        context.startActivity(starter);
    }
}
