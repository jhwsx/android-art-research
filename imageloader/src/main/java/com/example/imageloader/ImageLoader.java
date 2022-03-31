package com.example.imageloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.imageloader.cache.ImageCache;
import com.example.imageloader.cache.MemoryCache;
import com.example.imageloader.download.HttpURLConnectionDownloadCallbackImpl;
import com.example.imageloader.download.DownloadCallback;
import com.example.imageloader.util.ImageUtils;
import com.example.imageloader.util.Utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangzhichao
 * @since 2022/3/30
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static final int IMAGE_VIEW_URL_TAG = R.id.image_url;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    private static final int MESSAGE_LOAD_RESULT = 1;
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_LOAD_RESULT) {
                LoadResult result = (LoadResult) msg.obj;
                ImageView imageView = result.imageView;
                String url = (String) imageView.getTag(IMAGE_VIEW_URL_TAG);
                if (TextUtils.equals(url, result.url)) {
                    imageView.setImageBitmap(result.bitmap);
                } else {
                    Log.w(TAG, "handleMessage: url has changed, cannot set image bitmap, url=" +
                            url + ", result.url=" + result.url);
                }
            }
        }
    };

    private ImageCache imageCache = new MemoryCache();
    private DownloadCallback downloadCallback;

    public void displayImage(final ImageView iv, final String url) {
        displayImage(iv, url, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void displayImage(final ImageView iv, final String url, final int requestWidth, final int requestHeight) {
        iv.setTag(IMAGE_VIEW_URL_TAG, url);
        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = imageCache.get(url);
                if (bitmap != null) {
                    MAIN_HANDLER.obtainMessage(MESSAGE_LOAD_RESULT, new LoadResult(iv, url, bitmap)).sendToTarget();
                    return;
                }
                final Bitmap downloadImage = downloadImage(url, requestWidth, requestHeight);
                if (downloadImage != null) {
                    imageCache.put(url, downloadImage);
                    MAIN_HANDLER.obtainMessage(MESSAGE_LOAD_RESULT, new LoadResult(iv, url, downloadImage)).sendToTarget();
                }
            }
        });
    }

    private Bitmap downloadImage(String url, int requestWidth, int requestHeight) {
        BufferedInputStream bis = getDownloadCallback().download(url);
        byte[] bytes = new byte[0];
        try {
            bytes = readStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(bis);
        }
        return ImageUtils.decodeSampleBitmapFromByteArray(bytes, requestWidth, requestHeight);
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8 * 1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }

    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    public DownloadCallback getDownloadCallback() {
        if (downloadCallback == null) {
            downloadCallback = new HttpURLConnectionDownloadCallbackImpl();
        }
        return downloadCallback;
    }

    public void setDownloadCallback(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    public static class LoadResult {
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoadResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
