package com.wzc.chapter_3.elasticslide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/5/17.
 */

public class DelayStrategyLayout extends LinearLayout {
    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 100;
    private static final int DELAYED_TIME = 10;

    private int mCount = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount * 1f / FRAME_COUNT;
                        scrollTo(mScrollX + ((int) (fraction * mDeltaX)), 0);

                        Log.d("DelayStrategyLayout", "handleMessage: fraction = " + fraction
                                + ", mScrollX = " + mScrollX
                                + ", mDeltaX = " + mDeltaX);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_SCROLL_TO), DELAYED_TIME);
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    private int mScrollX;
    private int mDeltaX;

    public DelayStrategyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void smoothScrollTo(int destX, int destY) {
        mScrollX = getScrollX();
        mDeltaX = destX - mScrollX;

        mHandler.obtainMessage(MESSAGE_SCROLL_TO).sendToTarget();
    }
}
