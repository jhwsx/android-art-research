package com.wzc.chapter_3.slideconflict.type1.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 内部拦截法
 *
 * @author wzc
 * @date 2018/7/8
 */
public class ListViewEx extends ListView {
    private static final String TAG = ListViewEx.class.getSimpleName();
    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorizontalScrollViewEx2(HorizontalScrollViewEx2 horizontalScrollViewEx2) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    // record last slide coordinate
    private int mLastX = 0;
    private int mLastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent: ACTION_DOWN requestDisallowInterceptTouchEvent true");
                // 这一步的设置可以理解为在 ACTION_MOVE 开始之前, 就请求不要拦截;
                // 假如可以在 ACTION_MOVE 开始之后, 设置下面的一行代码, 那么子 View 必须可以接收到 ACTION_MOVE 事件,
                // 而这是不可能的, 因为父 View 里面会拦截 ACTION_MOVE 事件.
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) { // 父容器需要此类点击事件
                    Log.d(TAG, "dispatchTouchEvent: ACTION_MOVE requestDisallowInterceptTouchEvent false");
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "dispatchTouchEvent: ACTION_CANCEL");
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
