package com.wzc.chapter_2.util;

import android.os.Environment;

/**
 * @author wzc
 * @date 2018/4/4
 */
public interface MyConstants {
    String CHAPTER_2_PATH = Environment.getExternalStorageDirectory().getPath() + "/wzc/chapter_2/";
    String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";
    String MSG = "msg";
    String REPLY = "reply";
    static final int MSG_FROM_CLIENT = 1;
    static final int MSG_FROM_SERVICE = 2;
}
