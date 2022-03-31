package com.example.imageloader.download;

import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 默认的下载接口实现
 *
 * 使用 HttpURLConnection 实现下载
 *
 * @author wangzhichao
 * @since 2022/3/31
 */
public class HttpURLConnectionDownloadCallbackImpl implements DownloadCallback {
    @Override
    public BufferedInputStream download(String url) {
        BufferedInputStream bis = null;
        try {
            URL u = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
            if (httpURLConnection != null) {
                httpURLConnection.setReadTimeout(30 * 1000);
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bis = new BufferedInputStream(httpURLConnection.getInputStream());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bis;
    }
}
