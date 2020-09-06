package com.wzc.chapter_2.fileshare;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.wzc.chapter_2.model.Person;
import com.wzc.chapter_2.model.User;
import com.wzc.chapter_2.util.MyConstants;
import com.wzc.chapter_2.util.MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        recoverFromFileBySerializable();
        recoverFromFileByParcelable();
    }

    private void recoverFromFileBySerializable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cacheFile = new File(MyConstants.CACHE_SERIALIZABLE_FILE_PATH);
                FileInputStream fileInputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    fileInputStream = new FileInputStream(cacheFile);
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    User user = (User) objectInputStream.readObject();
                    Log.d(TAG, "recoverFromFileBySerializable: user = " + user);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fileInputStream);
                    MyUtils.closeQuietly(objectInputStream);
                }
                Log.d(TAG, "recoverFromFileBySerializable cost: " + (System.currentTimeMillis() - start));
            }
        };
        new Thread(runnable).start();
    }

    private void recoverFromFileByParcelable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cacheFile = new File(MyConstants.CACHE_PARCELABLE_FILE_PATH);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(cacheFile);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    Parcel parcel = Parcel.obtain();
                    parcel.unmarshall(bytes, 0, bytes.length);
                    parcel.setDataPosition(0);
                    Person person = parcel.readParcelable(Thread.currentThread().getContextClassLoader());
                    Log.d(TAG, "recoverFromFileByParcelable: person = " + person);
                    parcel.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fis);
                }
                Log.d(TAG, "recoverFromFileByParcelable cost: " + (System.currentTimeMillis() - start));
            }
        }).start();
    }
}
