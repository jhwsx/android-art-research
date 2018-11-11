package com.wzc.chapter_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author wzc
 * @date 2018/11/11
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
