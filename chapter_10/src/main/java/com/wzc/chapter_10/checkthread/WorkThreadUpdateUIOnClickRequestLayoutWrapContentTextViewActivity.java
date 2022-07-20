package com.wzc.chapter_10.checkthread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wzc.chapter_10.R;

public class WorkThreadUpdateUIOnClickRequestLayoutWrapContentTextViewActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapcontent_textview);
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestLayout();
                new Thread(() -> {
                    tv.setText("I am text set in " + Thread.currentThread().getName());
                },"work-thread").start();
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WorkThreadUpdateUIOnClickRequestLayoutWrapContentTextViewActivity.class);
        context.startActivity(starter);
    }
}
