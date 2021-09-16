package com.wzc.chapter_1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BActivity extends BaseLifecycleActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = new Button(this);
        button.setText("Button");
        button.setAllCaps(false);
        setContentView(button);

        postMessage();
    }

    private void postMessage() {
        button.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postMessage();
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BActivity.class);
        context.startActivity(starter);
    }
}
