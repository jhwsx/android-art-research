package com.example.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.Environment;

import com.example.imageloader.ImageLoader;
import com.example.imageloader.util.Utils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangzhichao
 * @since 2022/3/30
 */
public class DiskCache implements ImageCache {
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50; // 50MB
    private static final int VALUE_COUNT = 1;
    private static final int DISK_CACHE_INDEX = 0;
    private DiskLruCache diskLruCache;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    public DiskCache(Context context) {
        final File diskCacheDir = getDiskCacheDir(context, "bitmap");
        boolean mkdirsSuccess = true;
        if (!diskCacheDir.exists()) {
            mkdirsSuccess = diskCacheDir.mkdirs();
        }
        if (!mkdirsSuccess) {
            throw new IllegalStateException(diskCacheDir.getAbsolutePath() + "cannot be made");
        }
        ImageLoader.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                     * 参数一：硬盘缓存的存储路径
                     * 参数二：应用的版本号。当版本号发生改变时 DiskLruCache 会清除之前所有的缓存文件。这里希望应用版本号发生变化后保留缓存文件，所以传入 1.
                     * 参数三：单个节点所对应的数据的个数，一般设为 1 即可
                     * 参数四：缓存最大容量，单位为字节
                     */
                    diskLruCache = DiskLruCache.open(diskCacheDir, 1, VALUE_COUNT, DISK_CACHE_SIZE);
                    countDownLatch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Bitmap get(String url) {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        // 1，将 url 转换为 key
        String key = hashKeyFromUrl(url);
        FileInputStream fileInputStream = null;
        try {
            // 2，通过 get 方法获取一个 Snapshot 对象
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                // 3，通过 Snapshot 对象获取输入流对象
                fileInputStream = (FileInputStream) snapshot.
                        getInputStream(DISK_CACHE_INDEX);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                return BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(fileInputStream);
        }
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        // 1，将 url 转换为 key
        String key = hashKeyFromUrl(url);
        OutputStream outputStream = null;
        try {
            // 2，获取 Editor 对象
            // 对于这个 key 来说，如果当前不存在其他 Editor 对象，就会返回一个新的 Editor 对象
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            if (editor != null) {
                // 3，使用 Editor 对象创建一个输出流
                // 一个节点对应一个数据，所以这里传入索引为 0
                outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                // 4，将从网络获取的 Bitmap 对象存到文件输出流中
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    // 5，提交写入操作
                    editor.commit();
                } else {
                    // 5，回退整个操作
                    editor.abort();
                }
                diskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(outputStream);
        }
    }

    @Override
    public boolean remove(String url) {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        String key = hashKeyFromUrl(url);
        try {
            return diskLruCache.remove(key);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 删除所有缓存数据
     */
    @Override
    public void delete() {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将内存中的操作记录同步到日志文件（也就是journal文件）当中
     * <p>
     * 并不是每次写入缓存都要调用一次flush()方法的，频繁地调用并不会带来任何好处，只会额外增加同步journal文件的时间。
     * 比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
     */
    public void flush() {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        try {
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭掉 DiskLruCache
     * <p>
     * 和 open 方法相对应的一个方法。
     * 关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，通
     * 常只应该在Activity的onDestroy()方法中去调用close()方法
     */
    public void close() {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        try {
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前缓存路径下所有缓存数据的总字节数
     */
    @Override
    public long size() {
        try { countDownLatch.await();} catch (InterruptedException ignored) { }
        return diskLruCache.size();
    }

    /**
     * 将图片的链接进行 MD5 编码
     *
     * @param url 图片 url
     * @return
     */
    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes(StandardCharsets.UTF_8));
            cacheKey = byteToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable()
                && (context.getExternalCacheDir() != null)) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
