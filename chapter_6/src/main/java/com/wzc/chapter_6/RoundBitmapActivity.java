package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author wzc
 * @date 2018/12/29
 */
public class RoundBitmapActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, RoundBitmapActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_bitmap);

        ImageView ivRound = (ImageView) findViewById(R.id.iv_round);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        float radius =Math.min(bitmap.getWidth(), bitmap.getHeight())  / 2;
        Bitmap roundBitmap = roundBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), radius, radius, radius, radius);
        ivRound.setImageBitmap(roundBitmap);
    }

    public Bitmap roundBitmap(Bitmap bitmap, int destWidth, int destHeight, float f, float f2, float f3, float f4) {
        // 获取 bitmap 的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = new Rect(0, 0, width, height);
        Bitmap createBitmap = (width == destWidth && height == destHeight)
                ? Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                : Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect2 = new Rect(0, 0, destWidth, destHeight);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        float[] fArr = new float[]{f, f, f2, f2, f3, f3, f4, f4};
        // https://blog.csdn.net/Silk2018/article/details/19192329
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr,null, null));
        shapeDrawable.setBounds(rect2);
        shapeDrawable.draw(canvas);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect2,paint);
        return createBitmap;
    }


}
