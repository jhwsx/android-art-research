package com.wzc.chapter_3.elasticslide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_3.R;

/**
 * Created by wzc on 2018/5/15.
 */

public class ElasticSlideActivity extends Activity {

    private ScrollerLayout mScrollerLayout;
    private AnimLayout mAnimLayout;
    private DelayStrategyLayout mDelayStrategyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elastic_slide);

        mScrollerLayout = (ScrollerLayout) findViewById(R.id.scrollerlayout);
        findViewById(R.id.btn_scroller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollerLayout.smoothScrollTo(200,200);
            }
        });

        mAnimLayout = (AnimLayout) findViewById(R.id.animlayout);
         findViewById(R.id.btn_anim).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mAnimLayout.smoothScrollTo(200,200);
             }
         });

        mDelayStrategyLayout = (DelayStrategyLayout) findViewById(R.id.delayLayout);
        findViewById(R.id.btn_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelayStrategyLayout.smoothScrollTo(200,200);
            }
        });
    }
}
