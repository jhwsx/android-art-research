package com.wzc.chapter_1.activity36.part02_launchmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/10
 */
public class StandardActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, StandardActivity.class);
        context.startActivity(starter);
    }

    private static final String TAG = "StandardActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard_activity);
        Log.d(TAG, "onCreate: ");
        findViewById(R.id.btn_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardActivity.start(StandardActivity.this);
            }
        });
        findViewById(R.id.btn_singletop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTopActivity.start(StandardActivity.this);
            }
        });
        findViewById(R.id.btn_singletask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTaskActivity.start(StandardActivity.this);
            }
        });
        findViewById(R.id.btn_singleinstance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleInstanceActivity.start(StandardActivity.this);
            }
        });
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
        Log.d(TAG, "onConfigurationChanged:");
    }
}
