package com.wzc.chapter_10;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // define a ThreadLocal object
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBooleanThreadLocal.set(true);
        log();

        new Thread("Thread#1") {
            @Override
            public void run() {
                super.run();
                mBooleanThreadLocal.set(false);
                log();
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                super.run();
                log();
            }
        }.start();
    }

    private void log() {
        Log.d(TAG, "["+ getCurrThread() +"]"+ "mBooleanThreadLocal.get()=" + mBooleanThreadLocal.get());
    }

    private String getCurrThread() {
        return Thread.currentThread().getName();
    }

}
