package com.example.imageloader.download;

import java.io.BufferedInputStream;

/**
 * 下载接口
 *
 * @author wangzhichao
 * @since 2022/3/31
 */
public interface DownloadCallback {
    BufferedInputStream download(String url);
}
