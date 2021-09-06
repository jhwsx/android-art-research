package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author wzc
 * @date 2018/5/13
 */
public class DragView3 extends TextView {

    private static final String TAG = "DragView3";
    public DragView3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = x - mLastX;
                int dy = y - mLastY;
                // scrollBy 滑动的是 View 的内容，所以这里使用父控件的 scrollBy 方法实现 View 的滑动。
                // 但是，它滑动的是父控件的所有内容，而不单单是 View 一个。
                ((View) getParent()).scrollBy(-dx, -dy);
                // 输出没有任何变化
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
        }
        return true;
    }
}

//onTouchEvent: getLeft() = 0, getTop() = 550, getRight() = 275, getBottom() = 825, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 550.0
//onTouchEvent: getLeft() = 0, getTop() = 550, getRight() = 275, getBottom() = 825, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 550.0
//onTouchEvent: getLeft() = 0, getTop() = 550, getRight() = 275, getBottom() = 825, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 550.0
//onTouchEvent: getLeft() = 0, getTop() = 550, getRight() = 275, getBottom() = 825, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 550.0
//onTouchEvent: getLeft() = 0, getTop() = 550, getRight() = 275, getBottom() = 825, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 0.0, getY() = 550.0
