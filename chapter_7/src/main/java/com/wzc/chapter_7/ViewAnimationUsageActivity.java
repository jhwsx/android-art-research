package com.wzc.chapter_7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author wzc
 * @date 2018/10/14
 */
public class ViewAnimationUsageActivity extends Activity implements View.OnClickListener {
    public static void start(Context context) {
        Intent starter = new Intent(context, ViewAnimationUsageActivity.class);
        context.startActivity(starter);
    }

    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation_usage);
         findViewById(R.id.btn_1).setOnClickListener(this);
         findViewById(R.id.btn_2).setOnClickListener(this);
         findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_1) {
            startActivity(new Intent(mContext, Activity1.class));
            overridePendingTransition(R.anim.enter_top_in, R.anim.exit_bottom_out);
        } else if (id == R.id.btn_2) {
            startActivity(new Intent(mContext, Activity2.class));
        }else if (id == R.id.btn_3) {
            startActivity(new Intent(mContext, Activity3.class));
        }
    }
}
