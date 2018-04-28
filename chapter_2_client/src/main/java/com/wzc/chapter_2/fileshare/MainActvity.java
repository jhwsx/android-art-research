package com.wzc.chapter_2.fileshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.model.User;
import com.wzc.chapter_2.util.MyConstants;
import com.wzc.chapter_2.util.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class MainActvity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        persistToFile();
    }

    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    User user = new User(1, "hello", false);
                    File dir = new File(MyConstants.CHAPTER_2_PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                    fos = new FileOutputStream(cacheFile, false);
                    objectOutputStream = new ObjectOutputStream(fos);
                    objectOutputStream.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fos);
                    MyUtils.closeQuietly(objectOutputStream);
                }
            }
        }).start();
    }

    public void secondActivity(View view) {
        Intent intent = new Intent(MainActvity.this, SecondActivity.class);
        startActivity(intent);
    }
}
