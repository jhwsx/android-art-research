package com.wzc.chapter_1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

public class ActivityA extends Activity {
    private static final String TAG = ActivityA.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        findViewById(R.id.btn_secondactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityA.this, ActivityB.class);
                // 显式调用
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.wzc.chapter_2", "com.wzc.chapter_2.ActivityC"));
                // 隐式调用
                Intent intent = new Intent();
//                intent.setAction("com.wzc.chapter_1.action.d");
                intent.addCategory("com.wzc.chapter_1.category.c");
//                intent.addCategory("com.wzc.chapter_1.category.d");
                // 这个系统会默认添加
//                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("text/plain");
                startActivity(intent);
//                openWifiSettings();
            }
        });
        Log.d(TAG, "onCreate: taskId = "+getTaskId() + ", " );
    }

    public void openWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged, newOrietation: " + newConfig.orientation);
    }
}
