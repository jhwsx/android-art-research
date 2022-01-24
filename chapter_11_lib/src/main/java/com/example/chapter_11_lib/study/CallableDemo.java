package com.example.chapter_11_lib.study;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class CallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<Long>> futureList = executorService.invokeAll(Arrays.asList(new SumCallable(0, 10),
                new SumCallable(0, 1000), new SumCallable(0, 1_000_000)));
        for (Future<Long> future : futureList) {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }

    public static class SumCallable implements Callable<Long> {
        private long mFrom;
        private long mTo;

        public SumCallable(long from, long to) {
            mFrom = from;
            mTo = to;
        }

        @Override
        public Long call() throws Exception {
            long total = 0;
            for (long i = mFrom; i <= mTo; i++) {
                total += i;
            }
            System.out.println("call(): threadName="+Thread.currentThread().getName()+", total = " + total);

            return total;
        }
    }
}
