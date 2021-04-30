package com.wzc.chapter_8;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author wangzhichao
 * @since 2021/4/30
 */
public class FourthActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Dialog dialog = new Dialog(FourthActivity.this);
                dialog.setTitle("Title");
                dialog.show();
            }
        }, 1000L);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FourthActivity.class);
        context.startActivity(starter);
    }
}
