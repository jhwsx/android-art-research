package com.wzc.chapter_3.velocitytracker_gesturedetector_scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_3.R;

public class MarqueeTextViewActivity extends Activity {

    private MarqueeTextView mtv1;
    private MarqueeTextView mtv2;
    private MarqueeTextView mtv3;
    private MarqueeTextView mtv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_textview);
        mtv1 = (MarqueeTextView) findViewById(R.id.mtv1);
        mtv2 = (MarqueeTextView) findViewById(R.id.mtv2);
        mtv3 = (MarqueeTextView) findViewById(R.id.mtv3);
        mtv4 = (MarqueeTextView) findViewById(R.id.mtv4);
    }

    public void startScroll(View view) {
        mtv1.startScroll();
        mtv2.startScroll();
        mtv3.startScroll();
        mtv4.startScroll();
    }

    public void pauseScroll(View view) {
        mtv1.pauseScroll();
        mtv2.pauseScroll();
        mtv3.pauseScroll();
        mtv4.pauseScroll();
    }

    public void resumeScroll(View view) {
        mtv1.resumeScroll();
        mtv2.resumeScroll();
        mtv3.resumeScroll();
        mtv4.resumeScroll();
    }

    public void stopScroll(View view) {
        mtv1.stopScroll();
        mtv2.stopScroll();
        mtv3.stopScroll();
        mtv4.stopScroll();
    }


}
