package com.wzc.chapter_11.study;

import java.util.concurrent.Executor;

/**
 * @author wzc
 * @date 2018/8/26
 */
public class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static void main(String[] args) {
        DirectExecutor directExecutor = new DirectExecutor();
        directExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hh");
            }
        });
    }
}
