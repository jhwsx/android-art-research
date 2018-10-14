package com.wzc.chapter_7;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

/**
 * @author wzc
 * @date 2018/10/14
 */
public class PropertyAnimationActivity extends Activity {

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private IntEvaluator mIntValuator = new IntEvaluator();

    public static void start(Context context) {
        Intent starter = new Intent(context, PropertyAnimationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用 View 动画，会导致字体变形
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 500 * 1f / mBtn1.getWidth(), 1, 1, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                scaleAnimation.setDuration(2000);
                scaleAnimation.setFillAfter(true);
                mBtn1.startAnimation(scaleAnimation);
            }
        });
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(mBtn2, "width", 500).setDuration(2000).start();
            }
        });
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewWrapper viewWrapper = new ViewWrapper(mBtn3);
                ObjectAnimator.ofInt(viewWrapper, "width", mBtn3.getWidth(), 500).setDuration(2000).start();
            }
        });
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 获取当前动画的进度值，整型， 1 ~ 100
                        int currentValue = (int) animation.getAnimatedValue();
                        // 获取当前进度占整个动画过程的比例，浮点型，0 ~ 1
                        float fraction = animation.getAnimatedFraction();
                        Log.d("TAG", "currentValue:" + currentValue + ", fraction=" + fraction);

                        // 直接调用整型估值器，通过比例计算出宽度，然后再设置给 Button
                        mBtn4.getLayoutParams().width = mIntValuator.evaluate(fraction, mBtn4.getWidth(), 500);
                        mBtn4.requestLayout();
                    }
                });
                valueAnimator.setDuration(2000);
                valueAnimator.start();
            }
        });
    }

    private static class ViewWrapper {
        private View mTarget;

        private ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            Log.d("ViewWrapper", "getWidth()");
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            Log.d("ViewWrapper", "setWidth() width=" + width);
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }

}
