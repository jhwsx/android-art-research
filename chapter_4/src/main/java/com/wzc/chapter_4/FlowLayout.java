package com.wzc.chapter_4;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzc
 * @date 2021/9/23
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";

    private int lineSpacing = 60;
    private int itemHorizontalSpacing = 20;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthMeasureSpec=" + MeasureSpec.toString(widthMeasureSpec));
        Log.d(TAG, "onMeasure: heightMeasureSpec=" + MeasureSpec.toString(heightMeasureSpec));
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;
        int selfWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChildView(child, widthMeasureSpec, heightMeasureSpec);
                int childMeasuredWidth = child.getMeasuredWidth();
                int childMeasuredHeight = child.getMeasuredHeight();
                TextView textView = (TextView) child;
                String text = textView.getText().toString();
                if (lineWidth + (lineWidth == 0 ? 0 : itemHorizontalSpacing) + childMeasuredWidth + getPaddingLeft() + getPaddingRight() <= widthSize) {
                    // 当前行宽 + 一个 item 的宽度 + 父容器的左右内边距 小于 宽度的参考值，则可以添加到本行
                    lineWidth += (lineWidth == 0 ? 0 : itemHorizontalSpacing) + childMeasuredWidth;
                    lineHeight = Math.max(lineHeight, childMeasuredHeight);
                } else {
                    // 换行了
                    // 计算最大行宽
                    selfWidth = Math.max(lineWidth, selfWidth);
                    // 计算总高度
                    totalHeight += lineHeight;
                    totalHeight += lineSpacing;
                    // 新的一行开始了，把这个元素添加到新的一行里面
                    lineWidth = childMeasuredWidth;
                    lineHeight = childMeasuredHeight;
                }
            }

            if (i == childCount - 1) {
                selfWidth = Math.max(lineWidth, selfWidth);
                totalHeight += lineHeight;
            }
        }
        Log.d(TAG, "onMeasure: selfWidth=" + selfWidth);
        totalHeight += getPaddingTop() + getPaddingBottom();
        selfWidth += getPaddingLeft() + getPaddingRight();

        int finalWidth = resolveSize(selfWidth, widthMeasureSpec);
        int finalHeight = resolveSize(totalHeight, heightMeasureSpec);
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childCount = getChildCount();
        int lineHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();
            if (child.getVisibility() != View.GONE) {
                if (childLeft + childMeasuredWidth > getMeasuredWidth()) {
                    // 需要另起一行了
                    childLeft = getPaddingLeft();
                    childTop += lineHeight + lineSpacing;
                    lineHeight = 0;
                }
                child.layout(childLeft, childTop, childLeft + childMeasuredWidth, childTop + childMeasuredHeight);
                childLeft += child.getMeasuredWidth() + itemHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);
            }
        }
    }

    private void measureChildView(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        LayoutParams lp = child.getLayoutParams();
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width);
        int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingTop() + getPaddingBottom(), lp.height);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
}
