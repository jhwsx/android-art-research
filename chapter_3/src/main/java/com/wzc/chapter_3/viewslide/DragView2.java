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
public class DragView2 extends TextView {

    private static final String TAG = DragView2.class.getSimpleName();

    public DragView2(Context context, AttributeSet attrs) {
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

                layout(
                        getLeft() + dx,
                        getTop() + dy,
                        getRight() + dx,
                        getBottom() + dy
                );

                // 输出的值是变化的， 真正改变了 View 的位置参数
                Log.d(TAG, "onTouchEvent: getLeft() = " + getLeft()
                        + ", getTop() = " + getTop()
                        + ", getRight() = " + getRight()
                        + ", getBottom() = " + getBottom());
                mLastX = x;
                mLastY = y;
                break;
            }
            default:

        }
        return true;
    }
}
