package com.wzc.chapter_10;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Modifier;

/**
 * @author wzc
 * @date 2018/8/19
 */
public class MyActivity extends Activity {
    private static final String TAG = MyActivity.class.getSimpleName();
    private static final int MSG_CODE = 1;
    private static Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CODE:
                    Log.d(TAG, "接收到的消息是: " + msg.obj);
                    break;
                default:
            }
        }
    };

    /*
    System.currentTimeMillis()  系统时间，也就是日期时间，可以被系统设置修改，然后值就会发生跳变。

    SystemClock.uptimeMillis 自开机后，经过的时间，不包括深度睡眠的时间

    SystemClock.elapsedRealtime自开机后，经过的时间，包括深度睡眠的时间

    currentTimeMillis() 适合时间日期的显示
    uptimeMillis由于不计算睡眠时间，所以非常适合做一些特殊的时间间隔计算
    elapsedRealtime 包括睡眠时间，适用于任何情况下的时间间隔计算
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message msg = new Message();
        msg.what = MSG_CODE;
        msg.obj = "这是发送的消息";
        Log.d(TAG, "发送消息为" + msg.obj);
        sHandler.sendMessage(msg);
//        SystemClock.setCurrentTimeMillis(0);
//        long l = System.currentTimeMillis();
//        Log.d(TAG, "onCreate: l = " + l);

    }
}
