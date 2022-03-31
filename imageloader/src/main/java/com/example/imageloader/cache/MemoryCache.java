package com.example.imageloader.cache;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.imageloader.util.LruCache;

/**
 * @author wangzhichao
 * @since 2022/3/29
 */
public class MemoryCache implements ImageCache {
    private static final String TAG = "MemoryCache";
    public final LruCache<String, Bitmap> cache;

    public MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024); // kb
        int cacheSize = maxMemory / 8; // 32768kb=32M
        Log.d(TAG, "MemoryCache: cacheSize=" + cacheSize + "kb");
        cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024; // kb
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                Log.d(TAG, "entryRemoved: evicted=" + evicted + ",key=" + key +
                        ",oldValue=" + oldValue + ",newValue=" + newValue);
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return cache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }

    @Override
    public boolean remove(String url) {
        return cache.remove(url) != null;
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void delete() {
        cache.trimToSize(-1);
    }
}
