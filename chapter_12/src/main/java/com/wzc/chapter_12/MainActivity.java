package com.wzc.chapter_12;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.imageloader.cache.DiskCache;
import com.example.imageloader.cache.DoubleCache;
import com.example.imageloader.ImageLoader;
import com.example.imageloader.cache.MemoryCache;
import com.example.imageloader.util.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ImageView 的 scaleType
 * https://www.cnblogs.com/pandapan/p/4614837.html
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageView iv;
    private View btnBitmapCompress;
    private View btnSampleLoad;
    private View btnOriginalLoad;
    private View btnDrawableTest;
    private View btnMemoryCachePut;
    private View btnMemoryCacheGet;
    private View btnMemoryCacheShow;
    private final MemoryCache memoryCache = new MemoryCache();
    private DiskCache diskCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        diskCache = new DiskCache(this);
        printInfo();
        setupViews();
        setupListeners();
        testLinkedHashMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        diskCache.flush();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diskCache.close();
    }

    private void testLinkedHashMap() {
        /**
         * 第一个参数：初始化容量
         * 第二个参数：加载因子
         * 第三个参数：排序模式 - 对于访问顺序，为 true；对于插入顺序，则为 false
         */
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(
                0, 0.75f, true);
        linkedHashMap.put("one", 1);
        linkedHashMap.put("two", 2);
        linkedHashMap.put("three", 3);
        linkedHashMap.put("four", 4);
        Log.d(TAG, "onCreate: linkedHashMap=" + linkedHashMap);
        Log.d(TAG, "onCreate: linkedHashMap.get(\"one\")");
        linkedHashMap.get("one");
        Log.d(TAG, "onCreate: linkedHashMap=" + linkedHashMap);
        Log.d(TAG, "onCreate: linkedHashMap.get(\"two\")");
        linkedHashMap.get("two");
        Log.d(TAG, "onCreate: linkedHashMap=" + linkedHashMap);
    }

    private void printInfo() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d(TAG, "Max memory is " + maxMemory + "KB");
        iv = ((ImageView) findViewById(R.id.iv));
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        float width = widthPixels / xdpi;
        float height = heightPixels / ydpi;
        float size = (float) Math.sqrt(width * width + height * height);
        // density=2.75,densityDpi=440,xdpi=394.705,ydpi=394.716
        Log.d(TAG, "onCreate: density=" + density + ",densityDpi=" + densityDpi +
                ",xdpi=" + xdpi + ",ydpi=" + ydpi +
                ",width=" + width + ", height=" + height + ", size=" + size);
    }

    private void setupListeners() {
        btnBitmapCompress.setOnClickListener(this);
        btnDrawableTest.setOnClickListener(this);
        btnOriginalLoad.setOnClickListener(this);
        btnSampleLoad.setOnClickListener(this);
        btnMemoryCachePut.setOnClickListener(this);
        btnMemoryCacheGet.setOnClickListener(this);
        btnMemoryCacheShow.setOnClickListener(this);
    }

    private void setupViews() {
        btnBitmapCompress = findViewById(R.id.btn_bitmap_compress);
        btnDrawableTest = findViewById(R.id.btn_drawable_test);
        btnOriginalLoad = findViewById(R.id.btn_original_load);
        btnSampleLoad = findViewById(R.id.btn_sample_load);
        btnMemoryCachePut = findViewById(R.id.btn_memory_cache_put);
        btnMemoryCacheGet = findViewById(R.id.btn_memory_cache_get);
        btnMemoryCacheShow = findViewById(R.id.btn_memory_cache_show);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bitmap_compress:
                compressBitmap();
                break;
            case R.id.btn_drawable_test:
                DrawableTestActivity.start(MainActivity.this);
                break;
            case R.id.btn_original_load:
                originalLoad();
                break;
            case R.id.btn_sample_load:
                sampleLoad();
                break;
            case R.id.btn_memory_cache_put:
                putMemoryCache();
                break;
            case R.id.btn_memory_cache_get:
                getMemoryCache();
                break;
            case R.id.btn_memory_cache_show:
                showMemoryCache();
                break;
            default:
                break;
        }
    }

    private void showMemoryCache() {
        for (Map.Entry<String, Bitmap> entry : memoryCache.cache.snapshot().entrySet()) {
            Log.d(TAG, "showMemoryCache: key=" + entry.getKey() +
                    ", bitmap=" + entry.getValue() + ", size=" + (entry.getValue().getByteCount() / 1024) + "kb");
        }
    }

    private int[] drawableResArray = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7
    };
    private int index = 0;

    private void putMemoryCache() {
        index = index % drawableResArray.length;
        String resourceName = getResources().getResourceEntryName(drawableResArray[index]);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResArray[index]);
        Log.d(TAG, "putMemoryCache: key=" + resourceName + ", bitmap=" + bitmap +
                ", size=" + (bitmap.getByteCount() / 1024) + "kb");
        memoryCache.put(resourceName, bitmap);
        index++;
    }

    private void getMemoryCache() {
        int i = (index - 2) % 7;
        String resourceName = getResources().getResourceEntryName(drawableResArray[Math.max(i, 0)]);
        Bitmap bitmap = memoryCache.get(resourceName);
        Log.d(TAG, "getMemoryCache: key=" + resourceName + ", bitmap=" + bitmap +
                ", size=" + (bitmap.getByteCount() / 1024) + "kb");
    }

    private void compressBitmap() {
        final Bitmap bridgeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bridge);
        Log.d(TAG, "onCreate: bridgeBitmap width=" + bridgeBitmap.getWidth() +
                ",height=" + bridgeBitmap.getHeight() +
                ",byteCount=" + (bridgeBitmap.getByteCount()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos1 = null;
                FileOutputStream fos2 = null;
                FileOutputStream fos3 = null;
                try {
                    File dir = getExternalCacheDir();
                    fos1 = new FileOutputStream(new File(dir, "bridge.png"));
                    boolean result1 = bridgeBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos1);
                    Log.d(TAG, "run: result1=" + result1);
                    fos2 = new FileOutputStream(new File(dir, "bridge.jpg"));
                    boolean result2 = bridgeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos2);
                    Log.d(TAG, "run: result2=" + result2);
                    fos3 = new FileOutputStream(new File(dir, "bridge.webp"));
                    boolean result3 = bridgeBitmap.compress(Bitmap.CompressFormat.WEBP, 100, fos3);
                    Log.d(TAG, "run: result3=" + result3);
                    Bitmap fileBitmap = BitmapFactory.decodeFile(new File(dir, "bridge.jpg").getAbsolutePath());
                    Log.d(TAG, "onCreate: fileBitmap width=" + fileBitmap.getWidth() +
                            ",height=" + fileBitmap.getHeight() +
                            ",byteCount=" + (fileBitmap.getByteCount() / 1024) + "kb" +
                            ", calculateSize = " + (4 * fileBitmap.getWidth() * fileBitmap.getHeight() / 1024) + "kb");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fos1 != null) {
                        try {
                            fos1.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (fos2 != null) {
                        try {
                            fos2.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (fos3 != null) {
                        try {
                            fos3.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        }).start();
    }

    private void originalLoad() {
         /*
                2022-03-14 07:26:26.436 6363-6363/com.wzc.chapter_12 E/AndroidRuntime: FATAL EXCEPTION: main
            Process: com.wzc.chapter_12, PID: 6363
            java.lang.RuntimeException: Canvas: trying to draw too large(363000000bytes) bitmap.
                at android.graphics.RecordingCanvas.throwIfCannotDraw(RecordingCanvas.java:280)
                at android.graphics.BaseRecordingCanvas.drawBitmap(BaseRecordingCanvas.java:88)
                at android.graphics.drawable.BitmapDrawable.draw(BitmapDrawable.java:548)
                at android.widget.ImageView.onDraw(ImageView.java:1436)
                 */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.building);
        Log.d(TAG, "onClick: bitmap width=" + bitmap.getWidth() + ",height=" + bitmap.getHeight()
                + ",byteCount=" + (bitmap.getByteCount() / 1024 / 1024) + "MB");
        iv.setImageBitmap(bitmap);
    }

    private void sampleLoad() {
         /*BitmapFactory.Options options = new BitmapFactory.Options();
                // 采样率为 2，则采样后的图片其宽高均为原图大小的 1/2，像素就为原图的 1/4，内存占用大小也为原图的1/4.
                options.inSampleSize = 100;
                iv.setScaleType(ImageView.ScaleType.CENTER);
                ImageView.ScaleType scaleType = iv.getScaleType();
                Log.d(TAG, "onClick: scaleType=" + scaleType);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.building, options);*/
//                iv.setScaleType(ImageView.ScaleType.FIT_START);
        ImageView.ScaleType scaleType = iv.getScaleType();
        Log.d(TAG, "onClick: scaleType=" + scaleType);
        Bitmap bitmap = ImageUtils.decodeSampleBitmapFromResource(getResources(), R.drawable.building,
                PxUtils.dp2px(200), PxUtils.dp2px(200));
        iv.setImageBitmap(bitmap);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.building, options);
        Log.d(TAG, "onClick: building outHeight=" + options.outHeight +
                ",outWidth=" + options.outWidth + ",byte count = " +
                (4 * options.outWidth * options.outHeight / 1024) + "kb, mimeType=" + options.outMimeType);
    }

    public void putDiskCache(View view) {
        index = index % drawableResArray.length;
        String resourceName = getResources().getResourceEntryName(drawableResArray[index]);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResArray[index]);
        Log.d(TAG, "putDiskCache: key=" + resourceName + ", bitmap=" + bitmap +
                ", size=" + (bitmap.getByteCount() / 1024) + "kb");
        diskCache.put(resourceName, bitmap);
        index++;
    }

    public void getDiskCache(View view) {
        int i = (index - 2) % 7;
        String resourceName = getResources().getResourceEntryName(drawableResArray[Math.max(i, 0)]);
        Bitmap bitmap = diskCache.get(resourceName);
        if (bitmap != null) {
            Log.d(TAG, "getDiskCache: key=" + resourceName + ", bitmap=" + bitmap +
                    ", size=" + (bitmap.getByteCount() / 1024) + "kb");
            iv.setImageBitmap(bitmap);
        } else {
            Log.d(TAG, "getDiskCache: no bitmap for key=" + resourceName);
        }
    }

    public void removeDiskCache(View view) {
        int i = (index - 2) % 7;
        String resourceName = getResources().getResourceEntryName(drawableResArray[Math.max(i, 0)]);
        Log.d(TAG, "removeDiskCache: key=" + resourceName + ", result=" + diskCache.remove(resourceName));
    }

    public void useImageLoader(View view) {
        try {
            String url = "https://profile.csdnimg.cn/7/C/B/1_willway_wang";
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.setImageCache(new DoubleCache(this));
            imageLoader.displayImage(iv, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}