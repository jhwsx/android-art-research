package com.wzc.chapter_2.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_2.R;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class SecondActivity extends Activity {
    private static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if (getIntent() != null) {
            String extra_data = getIntent().getStringExtra("extra_data");
            Log.d(TAG, "onCreate: extra_data = " + extra_data);
        }
    }

    public void thirdActivity(View view) {
        startService(new Intent(SecondActivity.this, MyIntentService.class));
    }
}
