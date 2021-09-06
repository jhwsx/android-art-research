package com.wzc.chapter_3.viewslide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author wzc
 * @date 2018/5/13
 */
public class DragView1 extends TextView {
    private static final String TAG = DragView1.class.getSimpleName();

    public DragView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastX;
    private int mLastY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = x;
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int dx = x - mLastX;
                int dy = y - mLastY;

                float oldTranslationX = getTranslationX();
                float newTranslationX = oldTranslationX + dx;

                float oldTranslationY = getTranslationY();
                float newTranslationY = oldTranslationY + dy;
                setTranslationX(newTranslationX);
                setTranslationY(newTranslationY);

                // 没有改变 View 的位置参数: left，right，top，bottom。变化的只是 translationX 和 translationY，x 和 y。
                Log.d(TAG, "onTouchEvent: getLeft() = " + getLeft()
                        +", getTop() = " + getTop()
                        +", getRight() = " + getRight()
                        +", getBottom() = " + getBottom()
                        +", getTranslationX() = " + getTranslationX()
                        +", getTranslationY() = " + getTranslationY()
                        +", getX() = " + getX()
                        +", getY() = " + getY()
                );

                mLastX = x;
                mLastY = y;
                break;
            }
            default:

                break;

        }
        return true;
    }
}

//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 1.0, getTranslationY() = 0.0, getX() = 1.0, getY() = 0.0
//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 6.0, getTranslationY() = 1.0, getX() = 6.0, getY() = 1.0
//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 13.0, getTranslationY() = 4.0, getX() = 13.0, getY() = 4.0
//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 20.0, getTranslationY() = 7.0, getX() = 20.0, getY() = 7.0
//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 28.0, getTranslationY() = 11.0, getX() = 28.0, getY() = 11.0
//D/DragView1: onTouchEvent: getLeft() = 0, getTop() = 0, getRight() = 275, getBottom() = 275, getTranslationX() = 36.0, getTranslationY() = 15.0, getX() = 36.0, getY() = 15.0
