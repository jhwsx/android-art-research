package com.wzc.chapter_4;

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
 * @date 2018/8/11
 */
public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private int mChildWidth;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

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
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;

    /**
     * 外部拦截法, 重写父容器的 onInterceptTouchEvent() 方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    // 如果新的 down 事件来到时, 弹性滑动还没结束, 就中断动画, 这样用户体验较好
                    mScroller.abortAnimation();
                    // 但是, 要记得加上这句拦截
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 当水平方向的距离大于竖直方向的距离时, 让父容器拦截事件
                    intercepted = true;
                } else {
                    // 当水平方向的距离小于竖直方法的距离时, 父容器不拦截事件
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        Log.d(TAG, "intercepted:" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastInterceptX = x;
        mLastInterceptY = y;
        return intercepted;
    }

    private int mCurrentIndex;
    private int mTargetIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                Log.d(TAG, "mCurrentIndex:" + mCurrentIndex);
                if (Math.abs(xVelocity) >= 50) {
                    Log.d(TAG, "xVelocity >= 50");
                    mTargetIndex = xVelocity > 0 /*大于 0, 表示从左向右滑*/ ? mCurrentIndex - 1 : mCurrentIndex + 1;
                } else {
                    Log.d(TAG, "xVelocity < 50");
                    mTargetIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                Log.d(TAG, "mTargetIndex:" + mTargetIndex);
                // 控制边界
                mTargetIndex = Math.min(Math.max(0, mTargetIndex), mChildrenSize - 1);
                Log.d(TAG, "mTargetIndex fine-tune : " + mTargetIndex);
                // 开始弹性滑动
                smoothScrollTo(mTargetIndex * mChildWidth);
                // 更新当前页索引
                mCurrentIndex = mTargetIndex;
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int destX) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        int childCount = getChildCount();
        // 测量每个子 View, 这样子 View 的 getMeasuredWidth() 和 getMeasuredHeight() 才会有值
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 取出 measureSpec 中保存的值, 代表了父容器对子 View 的要求
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0) {
            // 没有任何子元素
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST
                && heightMode == MeasureSpec.AT_MOST) {
            // 宽和高都采用了 wrap_content
            View child = getChildAt(0);
            final int childMeasuredWidth = child.getMeasuredWidth();
            final int childMeasuredHeight = child.getMeasuredHeight();
            measuredWidth = childMeasuredWidth * childCount;
            measuredHeight = childMeasuredHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // 宽采用了 wrap_content, 宽度就是所有子元素的宽度之和
            View child = getChildAt(0);
            final int childMeasuredWidth = child.getMeasuredWidth();
            measuredWidth = childMeasuredWidth * childCount;
            measuredHeight = heightSize;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // 高采用了 wrap_content, 高度就是第一个子元素的高度
            View child = getChildAt(0);
            final int childMeasuredHeight = child.getMeasuredHeight();
            measuredWidth = widthSize;
            measuredHeight = childMeasuredHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    private int mChildrenSize;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                mChildWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + mChildWidth,
                        childView.getMeasuredHeight());
                childLeft += mChildWidth;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
