package com.wzc.chapter_10.idlehandler;

import android.os.MessageQueue;

public class IUiRunnable implements MessageQueue.IdleHandler {
    private final Runnable runnable;

    public IUiRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public boolean queueIdle() {
        runnable.run();
        return false;
    }
}
