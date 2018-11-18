package com.wzc.chapter_7;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtnViewAnimation;
    private Button mBtnViewAnimationUsage;
    private Button mBtnPropertyAnimation;
    private Context mContext = this;
    private Button mBtnCustomFlipAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnViewAnimation = (Button) findViewById(R.id.btn_view_animation);
        mBtnViewAnimationUsage = (Button) findViewById(R.id.btn_view_animation_usage);
        mBtnPropertyAnimation = (Button) findViewById(R.id.btn_property_animation);
        mBtnCustomFlipAnimation = (Button) findViewById(R.id.btn_custom_flip_animation);
        mBtnViewAnimation.setOnClickListener(this);
        mBtnViewAnimationUsage.setOnClickListener(this);
        mBtnPropertyAnimation.setOnClickListener(this);
        mBtnCustomFlipAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_view_animation) {
            ViewAnimationActivity.start(mContext);
        } else if (id == R.id.btn_view_animation_usage) {
            ViewAnimationUsageActivity.start(mContext);
        } else if (id == R.id.btn_property_animation) {
            PropertyAnimationActivity.start(mContext);
        } else if (id == R.id.btn_custom_flip_animation) {
            CustomFlipAnimationActivity.start(mContext);
        }
    }
}
