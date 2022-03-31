package com.example.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author wangzhichao
 * @since 2022/3/31
 */
public class DoubleCache implements ImageCache {
    private MemoryCache memoryCache;
    private DiskCache diskCache;
    public DoubleCache(Context context) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(context);
    }
    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = diskCache.get(url);
        if (bitmap != null) {
            memoryCache.put(url, bitmap);
            return bitmap;
        }
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }

    @Override
    public boolean remove(String url) {
        return memoryCache.remove(url) || diskCache.remove(url);
    }

    @Override
    public void delete() {
        memoryCache.delete();
        diskCache.delete();
    }

    @Override
    public long size() {
        return memoryCache.size() + diskCache.size();
    }
}
