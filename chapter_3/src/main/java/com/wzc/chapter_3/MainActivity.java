package com.wzc.chapter_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wzc.chapter_3.elasticslide.ElasticSlideActivity;
import com.wzc.chapter_3.eventdispatch.ActivityEventDispatchActivity;
import com.wzc.chapter_3.eventdispatch.EventDispatchInterviewActivity;
import com.wzc.chapter_3.eventdispatch.ViewEventDispatchActivity;
import com.wzc.chapter_3.eventdispatch.ViewGroupEventDispatchActivity;
import com.wzc.chapter_3.slideconflict.type1.internal.SlideConflictInActivity;
import com.wzc.chapter_3.slideconflict.type1.external.SlideConflictExActivity;
import com.wzc.chapter_3.velocitytracker_gesturedetector_scroller.GestureDetectorActivity;
import com.wzc.chapter_3.velocitytracker_gesturedetector_scroller.MarqueeTextViewActivity;
import com.wzc.chapter_3.velocitytracker_gesturedetector_scroller.ScrollerActivity;
import com.wzc.chapter_3.velocitytracker_gesturedetector_scroller.VelocityTrackerActivity;
import com.wzc.chapter_3.viewslide.DragActivity;
import com.wzc.chapter_3.viewslide.ViewSlideActivity;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        findViewById(R.id.btn_velocity_tracker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VelocityTrackerActivity.class));
            }
        });

        findViewById(R.id.btn_gesture_detector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GestureDetectorActivity.class));
            }
        });

        findViewById(R.id.btn_scroller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollerActivity.class));
            }
        });
        findViewById(R.id.btn_marquee_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MarqueeTextViewActivity.class));
            }
        });

         findViewById(R.id.btn_view_slide).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, ViewSlideActivity.class));
             }
         });
         findViewById(R.id.btn_drag).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, DragActivity.class));
             }
         });
         findViewById(R.id.btn_elasticslide).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, ElasticSlideActivity.class));
             }
         });
         findViewById(R.id.btn_activity_event_dispatch).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, ActivityEventDispatchActivity.class));
             }
         });
         findViewById(R.id.btn_view_event_dispatch).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, ViewEventDispatchActivity.class));
             }
         });
         findViewById(R.id.btn_viewgroup_event_dispatch).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, ViewGroupEventDispatchActivity.class));
             }
         });
         findViewById(R.id.btn_event_dispatch_interview).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, EventDispatchInterviewActivity.class));
             }
         });
         findViewById(R.id.btn_slide_conflit_1_ex).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, SlideConflictExActivity.class));
             }
         });
         findViewById(R.id.btn_slide_conflict_1_in).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, SlideConflictInActivity.class));
             }
         });
    }
}
