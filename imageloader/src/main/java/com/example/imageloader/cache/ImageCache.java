package com.example.imageloader.cache;

import android.graphics.Bitmap;

/**
 * @author wangzhichao
 * @since 2022/3/31
 */
public interface ImageCache {
    Bitmap get(String url);

    void put(String url, Bitmap bitmap);

    boolean remove(String url);

    void delete();

    long size();
}
