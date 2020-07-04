package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author wzc
 * @date 2018/5/13
 */
public class DragView3 extends TextView {

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

                mLastX = x;
                mLastY = y;
                break;
            default:
        }
        return true;
    }
}
