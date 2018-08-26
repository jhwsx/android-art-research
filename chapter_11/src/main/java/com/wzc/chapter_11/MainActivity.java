package com.wzc.chapter_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void asynctask(View view) {
        startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));
    }

    public void intentservice(View view) {
        Intent service = new Intent(MainActivity.this, MyIntentService.class);
        for (int i = 0; i < 5; i++) {
            service.putExtra(MyIntentService.EXTRA_TASK, "task " + i);
            startService(service);
        }
    }
}
