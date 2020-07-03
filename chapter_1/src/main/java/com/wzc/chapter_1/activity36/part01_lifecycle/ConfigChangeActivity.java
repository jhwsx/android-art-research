package com.wzc.chapter_1.activity36.part01_lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * 横竖屏切换时
 * 1,不设置 Activity 的 android:configChanges 属性时,切屏会重新调用各个生命周期, 以及 onSaveInstanceState, onRestoreInstanceState 方法,
 * 但不会调用 onConfigurationChanged 方法;
 * 2,设置 Activity 的 android:configChanges 属性的值为 "orientation" 时, 切换屏幕时,
 * 在 Xiaomi Redmi Note 8 Pro Android 9, API 28 上不走生命周期方法,只走 onConfigurationChanged 方法
 * 在 HUAWEI H60-L01 Android 6.0, API 23 上在切横屏幕时会走各个生命周期方法以及 onSaveInstanceState,
 * onRestoreInstanceState onConfigurationChanged方法,,但切换回竖屏幕时,只走 onConfigurationChanged 方法
 * 3, 设置 Activity 的 android:configChanges 属性的值为 "orientation|keyboardHidden" 时, 切换屏幕时,
 * 与 2 相同的效果
 * 4, 3, 设置 Activity 的 android:configChanges 属性的值为 "orientation|keyboardHidden|screenSize" 时, 切换屏幕时,
 * 只会走 onConfigurationChanged 方法
 * 当 API >12 时，需要加入 screenSize 属性，否则屏幕切换时即使你设置了 orientation 系统也会重建 Activity
 * @author wangzhichao
 * @since 2020/01/10
 */
public class ConfigChangeActivity extends Activity {
    public static final String TAG = "ConfigChangeActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, ConfigChangeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
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
