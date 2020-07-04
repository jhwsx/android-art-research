package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * TODO 为什么滑动这个控件时，会导致 DragView2 马上回到初始位置？
 * @author wzc
 * @date 2018/11/11
 */
public class DragView4 extends TextView {
    public DragView4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private int mLastX;
    private int mLastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin += offsetX;
                params.topMargin += offsetY;
                setLayoutParams(params);
                mLastX = x;
                mLastY = y;
                break;
            default:
                break;
        }

        return true;
    }
}
