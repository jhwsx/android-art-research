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
    private List<List<View>> allLineViews = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

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
        lineHeights.clear();
        allLineViews.clear();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;
        int selfWidth = 0;
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            measureChildView(child, widthMeasureSpec, heightMeasureSpec);
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();
            TextView textView = (TextView) child;
            String text = textView.getText().toString();
            if (lineWidth + (lineWidth == 0 ? 0 : itemHorizontalSpacing) + childMeasuredWidth + getPaddingLeft() + getPaddingRight() <= widthSize) {
                // 当前行宽 + 一个 item 的宽度 + 父容器的左右内边距 小于 宽度的参考值，则可以添加到本行
                lineWidth += (lineWidth == 0 ? 0 : itemHorizontalSpacing) + childMeasuredWidth;
                lineViews.add(child);
                lineHeight = Math.max(lineHeight, childMeasuredHeight);
//                Log.d(TAG, "onMeasure: lineNum=" + allLineViews.size() + ":" + text);
            } else {
                // 换行了
                // 计算最大行宽
                selfWidth = Math.max(lineWidth, selfWidth);
                Log.d(TAG, "onMeasure: lineNum=" + allLineViews.size() + ":" + lineWidth );
                allLineViews.add(lineViews);
                lineHeights.add(lineHeight);
                // 计算总高度
                totalHeight += lineHeight;
                totalHeight += lineSpacing;
                // 清空一行内包含元素的列表
                lineViews = new ArrayList<>();
                // 新的一行开始了，把这个元素添加到新的一行里面
                lineWidth = childMeasuredWidth;
                lineViews.add(child);
                lineHeight = childMeasuredHeight;
//                Log.d(TAG, "onMeasure: lineNum=" + allLineViews.size() + ":" + text );
            }
            if (i == childCount - 1) {
                selfWidth = Math.max(lineWidth, selfWidth);
                Log.d(TAG, "onMeasure: lineNum=" + allLineViews.size() + ":" + lineWidth );
                allLineViews.add(lineViews);
                lineHeights.add(lineHeight);
                totalHeight += lineHeight;
            }
        }
        Log.d(TAG, "onMeasure: selfWidth=" + selfWidth);
        totalHeight += getPaddingTop() + getPaddingBottom();
        selfWidth += getPaddingLeft() + getPaddingRight();
        int finalWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : selfWidth;
        int finalHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight;
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        for (int i = 0; i < allLineViews.size(); i++) {
            List<View> lineViews = allLineViews.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                view.layout(childLeft, childTop, childLeft + view.getMeasuredWidth(), childTop + view.getMeasuredHeight());
                childLeft += view.getMeasuredWidth() + itemHorizontalSpacing;
            }
            childLeft = getPaddingLeft();
            childTop += lineHeights.get(i) + lineSpacing;
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
