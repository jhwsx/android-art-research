package com.wzc.chapter_2.binderpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author wzc
 * @date 2018/5/4
 */
public class Driver2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(5);
        Executor executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.execute(new WorkerRunnable(doneSignal, i));
        }
        doneSignal.await();
        System.out.println("All finished");
    }
}

class WorkerRunnable implements Runnable {
    private CountDownLatch doneSignal;
    private int i;

    public WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("do some work ..." + i);
        doneSignal.countDown();
    }
}
