package com.wzc.chapter_1.activity36.part01_lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author wangzhichao
 * @since 20-6-30
 */
public class EnterExitAnimActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    

    public static void start(Context context) {
        Intent starter = new Intent(context, EnterExitAnimActivity.class);
        context.startActivity(starter);

    }
}
