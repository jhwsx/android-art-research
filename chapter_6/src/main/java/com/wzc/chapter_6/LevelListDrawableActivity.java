package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wzc on 2018/9/13.
 */

public class LevelListDrawableActivity extends Activity {

    private ImageView mImageView;

    public static void start(Context context) {
        Intent starter = new Intent(context, LevelListDrawableActivity.class);
        context.startActivity(starter);
    }
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list_drawable);
        mImageView = (ImageView) findViewById(R.id.iv);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int remainder = count % 5;
                // 当图片是 src 时,有效;background 时,无效
//                mImageView.setImageLevel(remainder);
                // 当图片是 src 时,有效;background 时,崩溃mImageView.getDrawable() NPE
//                mImageView.getDrawable().setLevel(remainder);
                // 当图片是 background 时,有效
                mImageView.getBackground().setLevel(remainder);
                count++;
            }
        });
    }
}
