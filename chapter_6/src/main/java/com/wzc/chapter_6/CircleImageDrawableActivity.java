package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author wzc
 * @date 2018/9/17
 */
public class CircleImageDrawableActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, CircleImageDrawableActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image_drawable);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        iv.setImageDrawable(new CircleImageDrawable(bitmap));
    }

    class CircleImageDrawable extends Drawable {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private RectF mRectF = new RectF();
        private int mWidth;
        public CircleImageDrawable(Bitmap bitmap) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            mPaint.setShader(bitmapShader);
            mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
        }

        @Override
        public int getIntrinsicHeight() {
            return mWidth;
        }

        @Override
        public int getIntrinsicWidth() {
            return mWidth;
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
        public void setBounds(int left, int top, int right, int bottom) {
            super.setBounds(left, top, right, bottom);
            mRectF.set(left, top, right, bottom);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }
    }
}
