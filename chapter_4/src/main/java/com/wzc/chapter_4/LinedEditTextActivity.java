package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * @author wzc
 * @date 2018/8/5
 */
public class LinedEditTextActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, LinedEditTextActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lined_edittext);

    }
    /**
     * Defines a custom EditText View that draws lines between each line of text that is displayed.
     */
    public static class LinedEditText extends EditText {

        private static final String TAG = LinedEditText.class.getSimpleName();
        private Paint mPaint;
        private Rect mRect;

        // This constructor is used by LayoutInflater
        public LinedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.CYAN);
            // Creates a Rect and a Paint object, and sets the style and color of the Paint object.
            mRect = new Rect();
        }

        /**
         * This is called to draw the LinedEditText object
         * @param canvas The canvas on which the background is drawn.
         */
        @Override
        protected void onDraw(Canvas canvas) {
            // Gets the number of lines of text in the View.
            int count = getLineCount();
            Log.d(TAG, "count:" + count);
            // Gets the global Rect and Paint objects
            Rect rect = mRect;
            Paint paint = mPaint;
            /*
             * Draws one line in the rectangle for every line of text in the EditText
             */
            for (int i = 0; i < count; i++) {

                // Gets the baseline coordinates for the current line of text
                int baseLine = getLineBounds(i, rect);
                Log.d(TAG, "baseLine:" + baseLine);
                /*
                 * Draws a line in the background from the left of the rectangle to the right,
                 * at a vertical position one dip below the baseline, using the "paint" object
                 * for details.
                 */
                canvas.drawLine(rect.left, baseLine + 1, rect.right, baseLine +1, paint);
            }

            // Finishes up by calling the parent method
            super.onDraw(canvas);
        }
    }

}
