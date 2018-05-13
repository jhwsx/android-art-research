package com.wzc.chapter_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }
}
