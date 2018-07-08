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
public class HorizontalScrollViewEx2 extends ViewGroup {

    private static final String TAG = HorizontalScrollViewEx2.class.getSimpleName();
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private int mChildCount;

    public HorizontalScrollViewEx2(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 速度追踪
        mVelocityTracker = VelocityTracker.obtain();
        // 弹性滑动对象
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        // 父元素要默认拦截除了 ACTION_DOWN 以外的其他事件
        if (action == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
//            if (!mScroller.isFinished()) {
//                mScroller.abortAnimation();
//                return true;
//            }
            Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN don't intercept");
            return false;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: not ACTION_DOWN, so intercept");
            return true;
        }
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
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
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
        // 记录子 View 的左边界值
        int childLeft = 0;
        mChildCount = getChildCount();
        mChildWidth = getChildAt(0).getMeasuredWidth();
        for (int i = 0; i < mChildCount; i++) {
            View childView = getChildAt(i);
            // 参考任玉刚老师代码修改: 考虑到子 View 不可见的情况下, 就不进行布局了.
            if (childView.getVisibility() != View.GONE) {
                childView.layout(childLeft, 0, childLeft + mChildWidth, childView.getMeasuredHeight());
                childLeft += mChildWidth;
            }

        }

    }


    private void smoothScrollTo(int destX) {
        mScroller.startScroll(getScrollX(),0,destX - getScrollX(),500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),0);
            postInvalidate();
        }
    }
    // 在 View 和 Window 分离时, 回收内存
    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
