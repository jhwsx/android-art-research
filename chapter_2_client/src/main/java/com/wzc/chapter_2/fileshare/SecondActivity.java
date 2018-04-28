package com.wzc.chapter_2.fileshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.wzc.chapter_2.model.User;
import com.wzc.chapter_2.util.MyConstants;
import com.wzc.chapter_2.util.MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class SecondActivity extends Activity {
    private static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        recoverFromFile();
    }

    private void recoverFromFile() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                FileInputStream fileInputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    fileInputStream = new FileInputStream(cacheFile);
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    User user = (User) objectInputStream.readObject();
                    Log.d(TAG, "recoverFromFile: user = " + user);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fileInputStream);
                    MyUtils.closeQuietly(objectInputStream);
                }
            }
        };
        new Thread(runnable).start();
    }
}
