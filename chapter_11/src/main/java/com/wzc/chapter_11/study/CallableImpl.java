package com.wzc.chapter_11.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class CallableImpl implements Callable<String> {

    private String mAcceptString;

    public CallableImpl(String acceptString) {
        mAcceptString = acceptString;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1_000);
        System.out.println("call() : threadName = " + Thread.currentThread().getName());
        return mAcceptString + " append some chars and return it!";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableImpl callable = new CallableImpl("my callable test");
        FutureTask<String> futureTask = new FutureTask<>(callable);
        long start = System.currentTimeMillis();
        new Thread(futureTask).start();
        // 调用 get() 阻塞主线程, 否则, 主线程不会阻塞
        String result = futureTask.get();
        long end = System.currentTimeMillis();
        System.out.println("result: " + result);
        System.out.println("cost: " + (end - start));
    }
}
