package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * TODO 为什么滑动这个控件时，会导致 DragView2 马上回到初始位置？
 * @author wzc
 * @date 2018/11/11
 */
public class DragView4 extends TextView {
    private static final String TAG = "DragView4";
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
                // View 的位置参数：left，top，right，bottom 都变化了
                // translationX 和 translationY 没有变化，都是默认值 0
                // x 和 y 变化了。
                Log.d(TAG, "onTouchEvent: getLeft() = " + getLeft()
                        + ", getTop() = " + getTop()
                        + ", getRight() = " + getRight()
                        + ", getBottom() = " + getBottom()
                        + ", getTranslationX() = " + getTranslationX()
                        + ", getTranslationY() = " + getTranslationY()
                        + ", getX() = " + getX()
                        + ", getY() = " + getY()
                );
                mLastX = x;
                mLastY = y;
                break;
            default:
                break;
        }

        return true;
    }
}
//onTouchEvent: getLeft() = 0, getTop() = 825, getRight() = 275, getBottom() = 1100, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 825.0
//onTouchEvent: getLeft() = 1, getTop() = 826, getRight() = 276, getBottom() = 1101, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 1.0, getY() = 826.0
//onTouchEvent: getLeft() = 4, getTop() = 829, getRight() = 279, getBottom() = 1104, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 4.0, getY() = 829.0
//onTouchEvent: getLeft() = 9, getTop() = 836, getRight() = 284, getBottom() = 1111, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 9.0, getY() = 836.0
//onTouchEvent: getLeft() = 16, getTop() = 844, getRight() = 291, getBottom() = 1119, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 16.0, getY() = 844.0
//onTouchEvent: getLeft() = 24, getTop() = 852, getRight() = 299, getBottom() = 1127, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 24.0, getY() = 852.0
