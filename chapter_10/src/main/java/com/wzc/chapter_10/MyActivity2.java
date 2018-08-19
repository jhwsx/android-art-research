package com.wzc.chapter_10;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * @author wzc
 * @date 2018/8/19
 */
public class MyActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread("Thread1"){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Handler handler = new Handler();
            }
        }.start();
    }
}
