package com.wzc.chapter_4;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

/**
 * @author wzc
 * @date 2018/8/4
 */
public class LabelViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_view);
        LabelView labelView = (LabelView) findViewById(R.id.labelview_3);
        labelView.setText("Hello China");
        labelView.setTextColor(Color.RED);
        labelView.setTextSize(SizeUtils.dp2px(this, 40));
    }
}
