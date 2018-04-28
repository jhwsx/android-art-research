package com.wzc.chapter_2.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.wzc.chapter_2.R;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class ThirdActivity extends Activity {
    private static final String TAG = ThirdActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        int result = MySingleton.getInstance().result;
        Log.d(TAG, "onCreate: 获取到的计算结果是 " + result);
    }
}
