package com.wzc.chapter_11.study;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class RunnableImpl implements Runnable {
    private String mAcceptString;

    public RunnableImpl(String acceptString) {
        mAcceptString = acceptString;
    }

    @Override
    public void run() {
        System.out.println("run(): threadName=" + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(mAcceptString + " append some chars and print it!");
    }

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl("my runnable test");
        long start = System.currentTimeMillis();
        new Thread(runnable).start();
        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start));
    }
}
