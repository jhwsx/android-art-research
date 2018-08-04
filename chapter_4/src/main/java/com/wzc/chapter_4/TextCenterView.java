package com.wzc.chapter_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author wzc
 * @date 2018/8/4
 */
public class TextCenterView extends View {

    private Paint mPaint = new Paint();

    public TextCenterView(Context context) {
        this(context, null);
    }

    public TextCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制文字需要居中显示的区域
        mPaint.setColor(Color.CYAN);
        Rect rect = new Rect(((int) (getWidth() * 0.05)), 50, ((int) (getWidth() * 0.95)), 200);
        canvas.drawRect(rect, mPaint);

        // 理解 Paint.FontMetricsInt
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        Log.d("TextCenterView", fontMetricsInt.toString());

        // 绘制文字
        int baseline = rect.centerY() - (fontMetricsInt.top + fontMetricsInt.bottom) / 2;
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.CENTER);
        String string = "willwangwang6";
        canvas.drawText(string, rect.centerX(), baseline, mPaint);

        // 画出 baseline 线
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(rect.left, baseline, rect.right, baseline, mPaint);
        // 绘制 ascent 线
        int ascentLine = baseline + fontMetricsInt.ascent;
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(rect.left, ascentLine, rect.right, ascentLine, mPaint);
        // 绘制 top 线
        int topLine = baseline + fontMetricsInt.top;
        mPaint.setColor(Color.DKGRAY);
        canvas.drawLine(rect.left, topLine, rect.right, topLine, mPaint);
        // 绘制 descent 线
        int descentLine = baseline + fontMetricsInt.descent;
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(rect.left, descentLine, rect.right, descentLine, mPaint);
        // 绘制 bottom 线
        int bottomLine = baseline + fontMetricsInt.bottom;
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(rect.left, bottomLine, rect.right, bottomLine, mPaint);
    }
}
