package com.wzc.chapter_11;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class MyIntentService extends IntentService {
    private static final String TAG = MyIntentService.class.getSimpleName();
    public static final String EXTRA_TASK = "extra_task";

    public MyIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: startId = " + startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand startId:" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String task = intent.getStringExtra(EXTRA_TASK);
        Log.d(TAG, "onHandleIntent: " + task + " start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onHandleIntent: " + task + " finish");
//        stopSelfResult(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
