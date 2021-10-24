package com.wzc.chapter_6;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * @author wangzhichao
 * @since 2021/10/22
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.start(this);
        finish();
    }
}
