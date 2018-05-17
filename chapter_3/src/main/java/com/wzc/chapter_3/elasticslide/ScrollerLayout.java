package com.wzc.chapter_3.elasticslide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wzc on 2018/5/15.
 */

public class ScrollerLayout extends LinearLayout {
    private Scroller mScroller;
    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        // 1000 ms 内滑向 destX, 效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);

        invalidate();

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
