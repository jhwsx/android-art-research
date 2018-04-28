package com.wzc.chapter_2.contentprovider;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.wzc.chapter_2.R;

/**
 * @author wzc
 * @date 2018/4/12
 */
public class ProviderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://com.wzc.chapter_2_provider.book.provider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
    }
}
