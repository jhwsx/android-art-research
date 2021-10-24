package com.wzc.chapter_6;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CustomDrawable extends Drawable {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomDrawable(int color) {
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect r = getBounds();
        float centerX = r.exactCenterX();
        float centerY = r.exactCenterY();
        canvas.drawCircle(centerX, centerY, Math.min(centerX, centerY), paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
