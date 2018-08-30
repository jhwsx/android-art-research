package com.wzc.chapter_5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Demo1Activity extends Activity {
    public static final String EXTRA_TEXT = "com.wzc.chapter_5.extra_text";
    private static final String TAG = Demo1Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        TextView tv = (TextView) findViewById(R.id.tv);
        if (getIntent() != null) {
            Log.d(TAG, "demo1 receive intent = " + getIntent().hashCode());
            String text = getIntent().getStringExtra(EXTRA_TEXT);
            tv.setText(text == null ? "no" : text);
        }
    }
}
