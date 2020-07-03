package com.wzc.chapter_1.activity36.part02_launchmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * 栈内复用模式
 * 从别的应用启动这个 Activity, 那么会新建一个 task, 并在该 task 中启动这个 Activity
 * 从本应用启动这个 Activity,
 *  若已经位于栈顶,则不会创建新的 Activity: 生命周期: onPause -> onNewIntent -> onResume;
 *  若不位于栈顶,那么会把这个 Activity 上面的页面都销毁掉, 生命周期: onRestart -> onStart -> onNewIntent -> onResume
 *
 * @author wangzhichao
 * @since 2020/01/13
 */
public class SingleTaskActivity extends Activity {
    private static final String TAG = "SingleTaskActivity";
    public static void start(Context context) {
        Intent starter = new Intent(context, SingleTaskActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletask_activity);
        Log.d(TAG, "onCreate: ");
        findViewById(R.id.btn_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardActivity.start(SingleTaskActivity.this);
            }
        });
        findViewById(R.id.btn_singletop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTopActivity.start(SingleTaskActivity.this);
            }
        });
        findViewById(R.id.btn_singletask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTaskActivity.start(SingleTaskActivity.this);
            }
        });
        findViewById(R.id.btn_singleinstance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleInstanceActivity.start(SingleTaskActivity.this);
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
