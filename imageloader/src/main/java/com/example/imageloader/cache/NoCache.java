package com.example.imageloader.cache;

import android.graphics.Bitmap;

/**
 * @author wangzhichao
 * @since 2022/3/31
 */
public class NoCache implements ImageCache {
    @Override
    public Bitmap get(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {

    }

    @Override
    public boolean remove(String url) {
        return false;
    }

    @Override
    public void delete() {

    }

    @Override
    public long size() {
        return 0;
    }
}
