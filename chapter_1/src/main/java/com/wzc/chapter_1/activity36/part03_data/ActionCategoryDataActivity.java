package com.wzc.chapter_1.activity36.part03_data;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author wangzhichao
 * @since 20-7-2
 */
public class ActionCategoryDataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText(getIntent().toString());
    }
}
