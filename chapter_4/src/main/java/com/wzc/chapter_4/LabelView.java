package com.wzc.chapter_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        mPaint.setColor(mTextColor);
        requestLayout();
        invalidate();
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public void setText(String text) {
        mText = text;
        requestLayout();
        invalidate();
    }

    private int mTextColor;
    private float mTextSize;
    private String mText;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        mTextColor = ta.getColor(R.styleable.LabelView_textColor, Color.BLACK);
        int paddingLeft = (int) ta.getDimension(R.styleable.LabelView_paddingLeft, SizeUtils.dp2px(context, 2));
        int paddingRight = (int) ta.getDimension(R.styleable.LabelView_paddingRight, SizeUtils.dp2px(context, 2));
        int paddingTop = (int) ta.getDimension(R.styleable.LabelView_paddingTop, SizeUtils.dp2px(context, 2));
        int paddingBottom = (int) ta.getDimension(R.styleable.LabelView_paddingBottom, SizeUtils.dp2px(context, 2));
        mText = ta.getString(R.styleable.LabelView_text);
        mTextSize = ta.getDimension(R.styleable.LabelView_textSize, SizeUtils.dp2px(context, 16));
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        initPainter();
        ta.recycle();
    }

    private void initPainter() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mPaint.ascent(), mPaint);
    }

    /**
     * 决定 view 的高度
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int result;
        if (heightMode == MeasureSpec.EXACTLY) {
            result = heightSize;
        } else {
            result = (int) (mPaint.descent() - mPaint.ascent() + getPaddingTop() + getPaddingBottom());
            if (heightMeasureSpec == MeasureSpec.AT_MOST) {
                // 要求的高度不能超过父类给的高度
                result = Math.min(result, heightSize);
            }
        }
        return result;
    }

    /**
     * 决定 view 的宽度
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int result;
        if (widthMode == MeasureSpec.EXACTLY) {
            result = widthSize;
        } else {
            result = (int) (mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight());
            if (widthMode == MeasureSpec.AT_MOST)
                result = Math.min(result, widthSize);
        }
        return result;
    }
}
