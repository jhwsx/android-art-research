package com.wzc.chapter_1.activity36.part01_lifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/09
 */
public class DialogActivity extends Activity {
    private static final String TAG = "DialogActivity";
    public static void start(Context context) {
        Intent starter = new Intent(context, DialogActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);
        Log.d(TAG, "onCreate: ");
        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new  AlertDialog.Builder(DialogActivity.this)
                        .setTitle("Dialog标题")
                       .setMessage("这是一段消息")
                       .create().show();
            }
        });
        findViewById(R.id.btn_dialog_theme_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemeActivity.start(DialogActivity.this);
            }
        });
        findViewById(R.id.btn_tranlucent_theme_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslucentThemeActivity.start(DialogActivity.this);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
