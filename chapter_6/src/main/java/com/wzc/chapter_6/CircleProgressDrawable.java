package com.wzc.chapter_6;

import android.annotation.ColorRes;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * @author wzc
 * @date 2018/10/22
 */
public class CircleProgressDrawable extends Drawable {
    private final Paint.FontMetrics mFontMetrics;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRingWidth = 5;
    private int mProgress;
    private RectF mArcRectF = new RectF();

    public CircleProgressDrawable(Context context, @ColorRes int color, int progress) {
        mPaint.setColor(context.getResources().getColor(android.R.color.background_dark));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(dpToPx(10, context));
        mFontMetrics = mPaint.getFontMetrics();
        mProgress = progress;
    }

    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        int size = Math.min(bounds.width(), bounds.height());
        canvas.translate(size / 2, size / 2);
        // 绘制圆弧
        mPaint.setStrokeWidth(mRingWidth);
        mArcRectF.set(-size / 2, -size / 2, size / 2, size / 2);
        canvas.drawArc(mArcRectF, 270, 90, false, mPaint);
        // 绘制文字
        mPaint.setStrokeWidth(1);
        float baseline = mArcRectF.centerY() - (mFontMetrics.top + mFontMetrics.bottom) / 2;
        canvas.drawText(String.valueOf(mProgress), mArcRectF.centerX(), baseline, mPaint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }
}
