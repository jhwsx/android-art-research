package com.wzc.chapter_3.elasticslide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_3.R;

/**
 * Created by wzc on 2018/5/15.
 */

public class ScrollerActivitiy extends Activity {

    private ScrollerLayout mScrollerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        mScrollerLayout = (ScrollerLayout) findViewById(R.id.scrollerlayout);
        findViewById(R.id.btn_scroller).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mScrollerLayout.smoothScrollTo(200,200);
             }
         });

    }
}
