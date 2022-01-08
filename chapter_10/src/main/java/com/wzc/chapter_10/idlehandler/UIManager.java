package com.wzc.chapter_10.idlehandler;

import android.os.Looper;
import android.os.MessageQueue;

import com.wzc.chapter_10.idlehandler.IUiRunnable;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private UIManager() {
        //no instance
    }

    /**
     * 添加任务到IdleHandler
     *
     * @param runnable runnable
     */
    public static void run(Runnable runnable) {
        IUiRunnable uiRunnable = new IUiRunnable(runnable);
        Looper.getMainLooper().myQueue().addIdleHandler(uiRunnable);
    }

    private static List<Runnable> uiTasks = new ArrayList<>();

    public static void addTask(Runnable runnable) {
        uiTasks.add(runnable);
    }

    public static void runUiTasks() {
        MessageQueue.IdleHandler iUiTask = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (!uiTasks.isEmpty()) {
                    Runnable task = uiTasks.get(0);
                    task.run();
                    uiTasks.remove(task);
                }
                //逐次取一个任务执行 避免占用主线程过久
                return !uiTasks.isEmpty();
            }
        };

        Looper.myQueue().addIdleHandler(iUiTask);
    }



}
