package com.wzc.chapter_4;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wzc
 * @date 2018/8/4
 */
public class LabelView extends View {

    private Paint mPaint;
    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(SizeUtils.dp2px(context, 16));
        mPaint.setColor(Color.DKGRAY);
    }



}
