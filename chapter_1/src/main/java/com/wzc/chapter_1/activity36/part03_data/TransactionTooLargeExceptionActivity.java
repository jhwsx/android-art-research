package com.wzc.chapter_1.activity36.part03_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/13
 */
public class TransactionTooLargeExceptionActivity extends Activity {
    private static final String EXTRA_BITMAP = "extra_bitmap";
    public static void start(Context context, Bitmap bitmap) {
        Intent starter = new Intent(context, TransactionTooLargeExceptionActivity.class);
        starter.putExtra(EXTRA_BITMAP, bitmap);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactiontoolargeexception_activity);
    }
}
