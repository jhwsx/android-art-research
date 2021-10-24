package com.wzc.chapter_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by wzc on 2018/9/14.
 */

public class ClipDrawableActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, ClipDrawableActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_drawable);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        final SeekBar sb = (SeekBar) findViewById(R.id.sb);
        final TextView tv = (TextView) findViewById(R.id.tv);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb);
        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.pb2);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int max = seekBar.getMax();
                double percent = progress * 1d / max;
                ClipDrawable clipDrawable = (ClipDrawable) iv.getBackground();
                clipDrawable.setLevel((int) (percent * 10000f));
                tv.setText(Integer.toString(progress));
                progressBar.setProgress(progress);
                progressBar2.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
