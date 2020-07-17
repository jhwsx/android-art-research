package com.wzc.chapter_3.slideconflict.type1.external;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 外部拦截法
 *
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
                // 参考任玉刚老师代码修改: 在弹性滚动没有结束时, 按下手指, 停止滚动, 立即拦截
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    // 如果不加下面这句代码, 那么在进行水平滑动时, 还未结束水平滑动松开手指,另一手指就马上进行
                    // 竖直滑动, 则水平方向的滑动已经被 abort 了, 新的滑动是竖直滑动. 这时,
                    // 屏幕上的情况是水平方向没有滑到终点, 而竖直方向可以滚动. 见书上 p164页.

                    // 加上这句代码, 那么当水平滑动正在进行时(进入这个 if 语句,就说明水平滑动正在进行),
                    // 那么直接拦截(即新的点击事件仍然交给父容器处理).
                    intercepted = true;
                }
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
