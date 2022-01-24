package com.wzc.chapter_10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author wangzhichao
 * @since 2022/1/20
 */
public class ActivityANRTestActivity extends Activity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_test);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ActivityANRTestActivity.class);
        context.startActivity(starter);
    }
}
