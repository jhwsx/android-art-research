package com.wzc.chapter_2.fileshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.model.Book;
import com.wzc.chapter_2.model.Person;
import com.wzc.chapter_2.model.User;
import com.wzc.chapter_2.util.MyConstants;
import com.wzc.chapter_2.util.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 通过 Serializable 序列化与反序列化来进行文件传输。
 * 通过 Parcelable 序列化与反序列化来进行文件传输。
 *
 * @author wzc
 * @date 2018/4/4
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        persistToFileBySerializable();
        persistToFileByParcelable();
    }

    private void persistToFileBySerializable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                FileOutputStream fos = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    User user = new User(1, "hello", false);
                    File dir = new File(MyConstants.CHAPTER_2_PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File cacheFile = new File(MyConstants.CACHE_SERIALIZABLE_FILE_PATH);
                    fos = new FileOutputStream(cacheFile, false);
                    objectOutputStream = new ObjectOutputStream(fos);
                    objectOutputStream.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fos);
                    MyUtils.closeQuietly(objectOutputStream);
                }
                Log.d(TAG, "persistToFileBySerializable cost: " + (System.currentTimeMillis() - start));
            }
        }).start();
    }

    private void persistToFileByParcelable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                Person p = new Person(1, "hello", false);
                FileOutputStream fos = null;
                try {
                    File dir = new File(MyConstants.CHAPTER_2_PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File cacheFile = new File(MyConstants.CACHE_PARCELABLE_FILE_PATH);
                    fos = new FileOutputStream(cacheFile, false);
                    Parcel parcel = Parcel.obtain();
                    parcel.writeParcelable(p, 0);
                    // 获取 Parcel 对象的字节数组
                    byte[] bytes = parcel.marshall();
                    fos.write(bytes);
                    fos.flush();
                    parcel.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.closeQuietly(fos);
                }
                Log.d(TAG, "persistToFileByParcelable cost: " + (System.currentTimeMillis() - start));
            }
        }).start();
    }

    public void secondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Book book = new Book();
        book.setName("aar");
        book.setPerson(new Person(1, "wzc", true));
        intent.putExtra(SecondActivity.ARGS_BOOK, book);
        startActivity(intent);
    }
}
