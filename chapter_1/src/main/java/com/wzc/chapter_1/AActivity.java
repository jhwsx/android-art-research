package com.wzc.chapter_1;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AActivity extends BaseLifecycleActivity {

    private long startTime;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = new Button(this);
        button.setText("跳转到BActivity");
        button.setAllCaps(false);
        setContentView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BActivity.start(AActivity.this);
                finish();
                startTime = System.currentTimeMillis();
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 距离 finish(): " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 距离 finish(): " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 距离 finish(): " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
