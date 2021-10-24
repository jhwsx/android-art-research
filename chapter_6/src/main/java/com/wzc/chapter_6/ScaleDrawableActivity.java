package com.wzc.chapter_6;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 参考：
 * Android 自定义水平进度条的圆角进度
 * https://blog.csdn.net/lv_fq/article/details/51762209
 *
 * Created by wzc on 2018/9/14.
 */

public class ScaleDrawableActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, ScaleDrawableActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_drawable);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        ScaleDrawable scaleDrawable = (ScaleDrawable) ll.getBackground();
        scaleDrawable.setLevel(1);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator va = ValueAnimator.ofInt(0, 100);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int progress = (int) animation.getAnimatedValue();
                        pb.setProgress(progress);
                    }
                });
                va.setDuration(20000L);
                va.start();
            }
        });

    }
}
