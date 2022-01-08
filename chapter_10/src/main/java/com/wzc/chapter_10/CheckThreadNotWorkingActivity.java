package com.wzc.chapter_10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class CheckThreadNotWorkingActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_thread_not_working);
        tv = (TextView) findViewById(R.id.tv);
        new Thread(() -> {
//             SystemClock.sleep(1000L);
            tv.setText("I am text set in " + Thread.currentThread().getName());
        },"work-thread").start();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CheckThreadNotWorkingActivity.class);
        context.startActivity(starter);
    }
}
