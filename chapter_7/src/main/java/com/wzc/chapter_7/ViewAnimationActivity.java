package com.wzc.chapter_7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author wzc
 * @date 2018/10/14
 */
public class ViewAnimationActivity extends Activity {

    private Button mBtnOk;
    private EditText mEtPwd;
    private Context mContext = this;
    public static void start(Context context) {
        Intent starter = new Intent(context, ViewAnimationActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mEtPwd = (EditText) findViewById(R.id.et);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                mEtPwd.startAnimation(animation);
            }
        });
    }
}
