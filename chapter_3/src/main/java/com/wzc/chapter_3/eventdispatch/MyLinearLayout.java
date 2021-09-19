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
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: start");
        boolean handled = super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: end handled="+handled);
        return handled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: start");
        boolean intercepted = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: end intercepted = " + intercepted);
        return intercepted;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: start");
        boolean handled = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_UP");
                break;
            default:
                break;

        }
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: end handled = " + handled);
        return handled;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(ActivityEventDispatchActivity.TAG, "MyLinearLayout:requestDisallowInterceptTouchEvent: " +
                "disallowIntercept = " + disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
