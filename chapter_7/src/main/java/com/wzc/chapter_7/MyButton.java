package com.wzc.chapter_7;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBreadth(int width) {
        getLayoutParams().width = width;
        requestLayout();
    }

    public int getBreadth() {
        return getWidth();
    }

    public void setSize(PointF point) {
        getLayoutParams().width = (int) point.x;
        getLayoutParams().height = (int) point.y;
        requestLayout();
    }

    public PointF getSize() {
        return new PointF(getWidth(), getHeight());
    }
}

