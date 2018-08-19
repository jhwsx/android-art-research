package com.wzc.chapter_10;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message msg = new Message();
        msg.what = MSG_CODE;
        msg.obj = "这是发送的消息";
        Log.d(TAG, "发送消息为" + msg.obj);
        sHandler.sendMessage(msg);
    }
}
