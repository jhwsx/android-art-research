package com.example.imageloader.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wangzhichao
 * @since 2022/3/30
 */
public class Utils {
    private Utils() {
        //no instance
    }

    public static void close(Closeable closeable)  {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
