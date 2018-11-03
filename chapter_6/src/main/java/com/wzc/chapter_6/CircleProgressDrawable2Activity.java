package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author wzc
 * @date 2018/10/22
 */
public class CircleProgressDrawable2Activity extends Activity {

    private ImageView mImageView;

    public static void start(Context context) {
        Intent starter = new Intent(context, CircleProgressDrawable2Activity.class);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleprogressdrawable_2);
        mImageView = (ImageView) findViewById(R.id.iv_drawable);
        mImageView.setImageDrawable(new CircleProgressDrawable(this,0,0));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
