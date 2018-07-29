package com.wzc.chapter_4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wzc
 * @date 2018/7/29
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mWidth = 200;
    private int mHeight = 200;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 这句代码指的就是 super
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = mWidth;
            heightSize = mHeight;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mWidth;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mHeight;
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            result = size;
        } else if (specMode == MeasureSpec.AT_MOST
                || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }
}
