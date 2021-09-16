package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @author wangzhichao
 * @since 2021/9/16
 */
public class SimpleScrollableTextView extends TextView {

    private static final String TAG = "Scrollable";
    private Scroller scroller = new Scroller(getContext());
    private float lastX;
    private float lastY;
    private int totalDx;
    private int totalDy;

    public SimpleScrollableTextView(Context context) {
        this(context, null);
    }

    public SimpleScrollableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scroller != null && !scroller.isFinished()) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float currX = event.getRawX();
                float currY = event.getRawY();
                int dx = (int) (currX - lastX);
                int dy = (int) (currY - lastY);
                totalDx += dx;
                totalDy += dy;
                Log.d(TAG, "onTouchEvent: move event totalDx=" + totalDx + ",totalDy=" + totalDy);
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                lastX = x;
                lastY = y;
                return true;
            case MotionEvent.ACTION_UP:
                scroller.startScroll(0,
                        0,
                        -totalDx,
                        -totalDy,
                        3000);
                invalidate();
        }
        return super.onTouchEvent(event);
    }

    private int lastOffsetX;
    private int lastOffsetY;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            int currY = scroller.getCurrY();
            int dx = currX - lastOffsetX;
            int dy = currY - lastOffsetY;
            Log.d(TAG, "computeScroll: currX="+currX+",currY="+currY+",dx="+dx+",dy="+dy);
            offsetLeftAndRight(dx);
            offsetTopAndBottom(dy);
            postInvalidate();
            lastOffsetX = currX;
            lastOffsetY = currY;
        } else {
            lastOffsetX = 0;
            lastOffsetY = 0;
            totalDx = 0;
            totalDy = 0;
        }

    }
}
