package com.wzc.chapter_11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wzc
 * @date 2018/8/25
 */
public class AsyncTaskActivity extends Activity {
    private static final String TAG = AsyncTaskActivity.class.getSimpleName();
    private static final String url = "https://github.com/jhwsx/android-art-research/raw/"
            + "f6257e9f1e46848400f7ff2635991fd5a850d4f8/chapter_11/app-debug.apk";
    TextView mTvProgress;
    TextView mTvLength;
    private DownloadTask mDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        Button btnVersionEvolution = (Button) findViewById(R.id.btn_version_evolution);
        Button btnStartDownload = (Button) findViewById(R.id.btn_start_download);
        Button btnCancelDownload = (Button) findViewById(R.id.btn_cancel_download);
        mTvProgress = (TextView) findViewById(R.id.tv_download_progress);
        mTvLength = (TextView) findViewById(R.id.tv_download_length);
        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadTask = new DownloadTask(AsyncTaskActivity.this);
                mDownloadTask.execute(url);
            }
        });
        btnCancelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadTask.cancel(false);
            }
        });
        btnVersionEvolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask("AsyncTask#1").execute("");
                new MyAsyncTask("AsyncTask#2").execute("");
                new MyAsyncTask("AsyncTask#3").execute("");
                new MyAsyncTask("AsyncTask#4").execute("");
                new MyAsyncTask("AsyncTask#5").execute("");
            }
        });
    }

    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private String mName = "AsyncTask";

        public MyAsyncTask(String name) {
            super();
            mName = name;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TAG, s + " finished at " + simpleDateFormat.format(new Date()));
        }
    }
    public static class DownloadTask extends AsyncTask<String, Integer, Long> {
        private WeakReference<AsyncTaskActivity> weakReference;
        private ProgressDialog mProgressDialog;

        public DownloadTask(AsyncTaskActivity activity) {
            weakReference = new WeakReference<>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(weakReference.get());
            mProgressDialog.setTitle("下载");
            mProgressDialog.setMessage("获取数据中...");
            mProgressDialog.show();
            Log.d(TAG, "onPreExecute: threadName=" + Thread.currentThread().getName());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
            Log.d(TAG, "onProgressUpdate: progress=" + values[0] + ", threadName=" + Thread.currentThread().getName());
            AsyncTaskActivity asyncTaskActivity = weakReference.get();
            if (asyncTaskActivity != null) {
                asyncTaskActivity.mTvProgress.setText("下载进度: " + values[0] + "%");
            }

        }

        @Override
        protected Long doInBackground(String... urls) {
            Log.d(TAG, "doInBackground: threadName=" + Thread.currentThread().getName());
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            long contentLength = -1;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30 * 1000);
                connection.setReadTimeout(30 * 1000);
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return -1L;
                }
                contentLength = connection.getContentLength();
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(new File(weakReference.get().getExternalFilesDir(null), "download.apk"));
                byte[] buffer = new byte[4 * 1024];
                int total = 0;
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    total += length;
                    final int progress = (int) (total * 100f / contentLength);
                    if (isCancelled()) {
                        break;
                    }
                    publishProgress(progress);
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseUtils.closeIOQuietly(inputStream, outputStream);
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return contentLength;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "onPostExecute: threadName=" + Thread.currentThread().getName());
            AsyncTaskActivity asyncTaskActivity = weakReference.get();
            if (asyncTaskActivity != null) {
                asyncTaskActivity.mTvLength.setText("下载字节数: " + aLong);
                asyncTaskActivity.mTvLength.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "onCancelled: " + Thread.currentThread().getName());
            AsyncTaskActivity asyncTaskActivity = weakReference.get();
            if (asyncTaskActivity != null) {
                asyncTaskActivity.mTvLength.setVisibility(View.VISIBLE);
                asyncTaskActivity.mTvLength.setText("任务已取消");
            }
        }
    }
}
