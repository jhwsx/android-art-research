package com.wzc.chapter_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_3.elasticslide.ElasticSlideActivity;
import com.wzc.chapter_3.eventdispatch.EventDispatchActivity;
import com.wzc.chapter_3.slideconflict.SlideConflit1ExActivity;
import com.wzc.chapter_3.slideconflict_practice1.SlideConflict1ExPractice1Activity;
import com.wzc.chapter_3.viewslide.DragActivity;
import com.wzc.chapter_3.viewslide.ViewSlideActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
