package com.wzc.chapter_1;

import android.app.Application;
import android.util.Log;

/**
 * @author wangzhichao
 * @since 20-7-2
 */
public class App extends Application {
    private static final String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e(TAG, "uncaughtException: threadName = " + Thread.currentThread().getName(), ex);
            }
        });
    }
}
