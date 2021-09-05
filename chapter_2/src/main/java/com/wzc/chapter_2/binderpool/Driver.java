package com.wzc.chapter_2.binderpool;

import java.util.concurrent.CountDownLatch;

/**
 * 这个例子来自 CountDownLatch 的 api
 * 有1个driver和5个worker，需要满足以下两点要求：
 * <p>
 *
 * 当driver完成了全部的工作之后才允许worker们开始工作；
 *
 * 当所有的worker都完成了自己的工作之后，driver主线程才能结束。
 *
 * @author wzc
 * @date 2018/5/4
 */
public class Driver {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        System.out.println("driver is doing something");
        System.out.println("driver is finished, start all workers ...");
        startSignal.countDown(); // 让 worker 线程开始执行
        doneSignal.await(); // 等待所有的 worker 结束
        System.out.println("All finished.");
    }


}

class Worker implements Runnable {
    CountDownLatch startSignal;
    CountDownLatch doneSignal;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await(); // 等待 driver 线程执行完毕，worker 线程才能获取开始信号
            System.out.println("do some work...");
            doneSignal.countDown(); // 当前 worker 执行完毕，释放一个完成信号，也就是 count 会减去1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
