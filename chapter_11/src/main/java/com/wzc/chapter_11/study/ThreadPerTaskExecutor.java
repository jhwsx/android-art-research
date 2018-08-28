package com.wzc.chapter_11.study;

import java.util.concurrent.Executor;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

    public static void main(String[] args) {
        ThreadPerTaskExecutor threadPerTaskExecutor = new ThreadPerTaskExecutor();
        threadPerTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });
    }
}
