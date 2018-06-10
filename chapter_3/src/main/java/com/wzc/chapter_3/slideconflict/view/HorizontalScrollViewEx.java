package com.wzc.chapter_3.slideconflict.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author wzc
 * @date 2018/6/9
 */
public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private int mChildCount;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 速度追踪
        mVelocityTracker = VelocityTracker.obtain();
        // 弹性滑动对象
        mScroller = new Scroller(getContext());
    }

    private int mLastInterceptX;
    private int mLastInterceptY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
//                Log.d(TAG, "onInterceptHoverEvent: dx = " + dx + ", dy = " + dy);
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
        }

        mLastInterceptX = x;
        mLastInterceptY = y;
        mLastX = x;
        mLastY = y;
        Log.d(TAG, "onInterceptHoverEvent: intercepted = " + intercepted);
        return intercepted;
    }

    private int mLastX;
    private int mLastY;

    int currentIndex = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mVelocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int dx = x - mLastX;
                scrollBy(-dx, 0);
                break;
            }

            case MotionEvent.ACTION_UP: {
                // 计算 1000ms 内滑过的像素数
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                int destIndex;
                if (Math.abs(xVelocity) > 50) {
                    destIndex = xVelocity > 0 ? // > 0 右滑
                            currentIndex - 1 // 右滑时, 当前页索引 - 1
                            : currentIndex + 1; // 左滑时, 当前页索引 + 1
                } else {
                    destIndex = (getScrollX() + mChildWidth / 2) / mChildWidth;
                }
                destIndex = Math.min(Math.max(0, destIndex), mChildCount - 1);
                currentIndex = destIndex;
                Log.d(TAG, "onTouchEvent: destIndex = " + destIndex + ", mChildCount = " + mChildCount +", getScrollX() = " + getScrollX() + ", mChildWidth = " + mChildWidth);
                int destX = destIndex * mChildWidth;
                smoothScrollTo(destX);
                mVelocityTracker.clear();
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int width = resolveSize(measuredWidth, widthMeasureSpec);
        int height = resolveSize(measuredHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }
    private int mChildWidth;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildCount = getChildCount();
        mChildWidth = getChildAt(0).getMeasuredWidth();
        for (int i = 0; i < mChildCount; i++) {
            View childView = getChildAt(i);
            childView.layout(mChildWidth * i, 0, mChildWidth * (i + 1), childView.getMeasuredHeight());
        }

    }


    private void smoothScrollTo(int destX) {
        mScroller.startScroll(getScrollX(),0,destX - getScrollX(),300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),0);
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
