package com.wzc.chapter_10.checkthread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.wzc.chapter_10.R;

public class WorkThreadUpdateUIImmediatelyExactlyTextViewActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exactly_textview);
        tv = (TextView) findViewById(R.id.tv);
        new Thread(() -> {
            tv.setText("I am text set in " + Thread.currentThread().getName());
        },"work-thread").start();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WorkThreadUpdateUIImmediatelyExactlyTextViewActivity.class);
        context.startActivity(starter);
    }
}
