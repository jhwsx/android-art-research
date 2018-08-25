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
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wzc
 * @date 2018/8/25
 */
public class AsyncTaskActivity extends Activity {
    private static final String TAG = AsyncTaskActivity.class.getSimpleName();
    private static final String url = "https://github.com/jhwsx/android-art-research/raw/"
            +"f6257e9f1e46848400f7ff2635991fd5a850d4f8/chapter_11/app-debug.apk";
    private TextView mTvProgress;
    private TextView mTvLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        Button btnStartDownload = (Button) findViewById(R.id.btn_start_download);
        mTvProgress = (TextView) findViewById(R.id.tv_download_progress);
        mTvLength = (TextView) findViewById(R.id.tv_download_length);
        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(url);
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Integer, Long> {

        private ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AsyncTaskActivity.this);
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
            Log.d(TAG, "onProgressUpdate: threadName=" + Thread.currentThread().getName());
            mTvProgress.setText("下载进度: " + values[0] + "%");
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
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return -1L;
                }
                contentLength = connection.getContentLength();
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(new File(getExternalFilesDir(null), "download.apk"));
                byte[] buffer = new byte[4 * 1024];
                int total = 0;
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    total += length;
                    final int progress = (int) (total * 100f / contentLength);
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
            mTvLength.setText("下载字节数: " + aLong);
            mTvLength.setVisibility(View.VISIBLE);
        }
    }
}
