package com.wzc.chapter_3.elasticslide;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/5/17.
 */

public class AnimLayout extends LinearLayout {

    public AnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void smoothScrollTo(int destX, int destY) {
        final int scrollX = getScrollX();
        final int deltaX = destX - scrollX;

        ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                Log.d("AnimLayout", "onAnimationUpdate: fraction = " + fraction);
                scrollTo(scrollX + (int) (deltaX * fraction), 0);
            }
        });
        animator.start();
    }
}
