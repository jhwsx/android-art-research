package com.wzc.chapter_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void asynctask(View view) {
        startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));
    }

    public void intentservice(View view) {
        Intent service = new Intent(MainActivity.this, MyIntentService.class);
        for (int i = 0; i < 5; i++) {
            service.putExtra(MyIntentService.EXTRA_TASK, "task " + i);
            Log.d(TAG, "startService: task " + i);
            startService(service);
        }
    }

    static class MyHandlerThread extends HandlerThread {

        public MyHandlerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            SystemClock.sleep(3000L);
            super.run();
        }
    }

    public void handlerThread(View view) {
        final HandlerThread handlerThread = new MyHandlerThread("WorkThread");
        handlerThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: threadName=" + Thread.currentThread().getName());
                Looper looper = handlerThread.getLooper();
                Log.d(TAG, "run: threadName=" + Thread.currentThread().getName() + ",looper=" + looper);
            }
        }, "Thread1").start();
    }

    public void dontUseHandlerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                // ...
                Looper.loop();
            }
        }).start();
    }
}
