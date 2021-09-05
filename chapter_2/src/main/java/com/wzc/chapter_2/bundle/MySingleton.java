package com.wzc.chapter_2.bundle;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class MySingleton {
    private MySingleton() {
        //no instance
    }

    public static MySingleton getInstance() {
        return SingletonHolder.sSingleton;
    }

    private static final class SingletonHolder {
        private static MySingleton sSingleton = new MySingleton();
    }

    public int result;
}
