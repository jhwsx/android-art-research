package com.wzc.chapter_10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wzc.chapter_10.checkthread.WorkThreadUpdateUIImmediatelyExactlyTextViewActivity;
import com.wzc.chapter_10.checkthread.WorkThreadUpdateUIImmediatelyWrapContentTextViewActivity;
import com.wzc.chapter_10.checkthread.WorkThreadUpdateUIOnClickExactlyTextViewActivity;
import com.wzc.chapter_10.checkthread.WorkThreadUpdateUIOnClickRequestLayoutWrapContentTextViewActivity;
import com.wzc.chapter_10.checkthread.WorkThreadUpdateUISleep2sExactlyTextViewActivity;
import com.wzc.chapter_10.checkthread.WorkThreadUpdateUIOnClickWrapContentTextViewActivity;

public class CheckThreadActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_thread);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CheckThreadActivity.class);
        context.startActivity(starter);
    }

    public void workThreadUpdateUIImmediatelyExactlyTextView(View view) {
        WorkThreadUpdateUIImmediatelyExactlyTextViewActivity.start(this);
    }

    public void workThreadUpdateUISleep2sExactlyTextView(View view) {
        WorkThreadUpdateUISleep2sExactlyTextViewActivity.start(this);
    }

    public void workThreadUpdateUIOnClickExactlyTextView(View view) {
        WorkThreadUpdateUIOnClickExactlyTextViewActivity.start(this);
    }

    public void workThreadUpdateUIImmediatelyWrapContentTextView(View view) {
        WorkThreadUpdateUIImmediatelyWrapContentTextViewActivity.start(this);
    }

    public void workThreadUpdateUIOnClickWrapContentTextView(View view) {
        WorkThreadUpdateUIOnClickWrapContentTextViewActivity.start(this);
    }

    public void workThreadUpdateUIOnClickRequestLayoutWrapContentTextView(View view) {
        WorkThreadUpdateUIOnClickRequestLayoutWrapContentTextViewActivity.start(this);
    }
}
