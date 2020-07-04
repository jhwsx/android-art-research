package com.wzc.chapter_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wzc.chapter_3.elasticslide.ElasticSlideActivity;
import com.wzc.chapter_3.eventdispatch.EventDispatchActivity;
import com.wzc.chapter_3.eventdispatch.EventDispatchInterviewActivity;
import com.wzc.chapter_3.slideconflict.SlideConflict1InActivity;
import com.wzc.chapter_3.slideconflict.SlideConflict2ExActivity;
import com.wzc.chapter_3.slideconflict.SlideConflit1ExActivity;
import com.wzc.chapter_3.slideconflict_practice1.SlideConflict1ExPractice1Activity;
import com.wzc.chapter_3.viewslide.DragActivity;
import com.wzc.chapter_3.viewslide.ViewSlideActivity;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
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
         findViewById(R.id.btn_event_dispatch).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, EventDispatchActivity.class));
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
                 startActivity(new Intent(MainActivity.this, SlideConflit1ExActivity.class));
             }
         });
         findViewById(R.id.btn_slide_conflit_1_ex_practice_1).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, SlideConflict1ExPractice1Activity.class));
             }
         });
         findViewById(R.id.btn_slide_conflict_1_in).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, SlideConflict1InActivity.class));
             }
         });
         findViewById(R.id.btn_slide_conflict_2_ex).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 startActivity(new Intent(MainActivity.this, SlideConflict2ExActivity.class));
                 iv.setTranslationX(16);
                 iv.setTranslationY(16);
                 Log.d(TAG, "onClick: iv.getLeft()=" + iv.getLeft());
                 Log.d(TAG, "onClick: iv.getRight()=" + iv.getRight());
                 Log.d(TAG, "onClick: iv.getTop()=" + iv.getTop());
                 Log.d(TAG, "onClick: iv.getBottom()=" + iv.getBottom());
                 Log.d(TAG, "onClick: iv.getWidth()=" + iv.getWidth());
                 Log.d(TAG, "onClick: iv.getHeight()=" + iv.getHeight());
                 Log.d(TAG, "onClick: iv.getX()=" + iv.getX());
                 Log.d(TAG, "onClick: iv.getY()=" + iv.getY());
                 Log.d(TAG, "onClick: iv.getTranslationX()=" + iv.getTranslationX());
                 Log.d(TAG, "onClick: iv.getTranslationY()=" + iv.getTranslationY());



             }
         });
    }
}
