package com.wzc.chapter_1.activity36.part03_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/13
 */
public class DataActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, DataActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        findViewById(R.id.btn_transaction_too_large).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //      Caused by: android.os.TransactionTooLargeException: data parcel size 132710964 bytes
//                132710964
//                235929600
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
                Log.d("DataActivity", "bitmap.getByteCount():" + bitmap.getByteCount()); // 132710400
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                Log.d("DataActivity", "displayMetrics.density:" + displayMetrics.density); // 4.0
                Log.d("DataActivity", "displayMetrics.densityDpi:" + displayMetrics.densityDpi); // 640
                float scale = displayMetrics.densityDpi * 1f / 320;
                long size = (long) (bitmap.getWidth() * scale * bitmap.getHeight() * scale * 4);
                Log.d("DataActivity", "size:" + size);
                TransactionTooLargeExceptionActivity.start(DataActivity.this, bitmap);
            }
        });
    }
}
