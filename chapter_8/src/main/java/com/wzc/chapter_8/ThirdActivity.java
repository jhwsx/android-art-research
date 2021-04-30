package com.wzc.chapter_8;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 *
 * @author wangzhichao
 * @since 2021/4/30
 */
public class ThirdActivity extends Activity {
    private static final String TAG = "ThirdActivity";
    private Handler handler = new Handler();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(ThirdActivity.this);
        dialog.setTitle("Title");
        dialog.show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }, 1000L);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ThirdActivity.class);
        context.startActivity(starter);
    }
}
