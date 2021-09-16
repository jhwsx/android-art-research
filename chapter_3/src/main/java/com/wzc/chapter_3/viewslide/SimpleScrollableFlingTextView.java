package com.wzc.chapter_3.viewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @author wangzhichao
 * @since 2021/9/16
 */
public class SimpleScrollableFlingTextView extends TextView {

    private static final String TAG = "Scrollable";
    private Scroller scroller;
    private Scroller flinger;
    private VelocityTracker velocityTracker;
    private float lastX;
    private float lastY;
    private int totalDx;
    private int totalDy;
    private float xVelocity;
    private float yVelocity;

    public SimpleScrollableFlingTextView(Context context) {
        this(context, null);
    }

    public SimpleScrollableFlingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scroller != null && !scroller.isFinished()) {
            return super.onTouchEvent(event);
        }
        if (flinger != null && !flinger.isFinished()) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                xVelocity = velocityTracker.getXVelocity();
                yVelocity = velocityTracker.getYVelocity();
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
                velocityTracker.recycle();
                velocityTracker = null;
                flinger = new Scroller(getContext());
                flinger.fling(getScrollX(), getScrollY(), (int) xVelocity, (int) yVelocity,
                        Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                invalidate();
        }
        return super.onTouchEvent(event);
    }

    private int lastOffsetX;
    private int lastOffsetY;

    private int lastFlingOffsetX;
    private int lastFlingOffsetY;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (flinger != null) {
            if (flinger.computeScrollOffset()) {
                int currX = flinger.getCurrX();
                int currY = flinger.getCurrY();
                int dx = currX - lastFlingOffsetX;
                int dy = currY - lastFlingOffsetY;
                Log.d(TAG, "computeScroll fling: currX=" + currX + ",currY=" + currY + ",dx=" + dx + ",dy=" + dy);
                totalDx += dx;
                totalDy += dy;
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                postInvalidate();
                lastFlingOffsetX = currX;
                lastFlingOffsetY = currY;
            } else {
                lastFlingOffsetX = 0;
                lastFlingOffsetY = 0;
                scroller = new Scroller(getContext());
                // fling 结束了
                scroller.startScroll(0,
                        0,
                        -totalDx,
                        -totalDy,
                        3000);
                flinger = null;
            }
        }
        if (scroller != null) {
            if (scroller.computeScrollOffset()) {
                int currX = scroller.getCurrX();
                int currY = scroller.getCurrY();
                int dx = currX - lastOffsetX;
                int dy = currY - lastOffsetY;
                Log.d(TAG, "computeScroll scroll: currX="+currX+",currY="+currY+",dx="+dx+",dy="+dy);
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                postInvalidate();
                lastOffsetX = currX;
                lastOffsetY = currY;
            } else {
                scroller = null;
                lastOffsetX = 0;
                lastOffsetY = 0;
                totalDx = 0;
                totalDy = 0;
            }
        }
    }
}
