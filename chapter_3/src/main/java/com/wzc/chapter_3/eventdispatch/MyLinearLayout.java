package com.wzc.chapter_3.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/5/26.
 */

public class MyLinearLayout extends LinearLayout {


    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: intercepted = " + intercepted);
        return intercepted;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_UP");
                break;
            default:
                break;

        }
        Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: handled = " + handled);
        return handled;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(EventDispatchActivity.TAG, "MyLinearLayout:requestDisallowInterceptTouchEvent: " +
                "disallowIntercept = " + disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
