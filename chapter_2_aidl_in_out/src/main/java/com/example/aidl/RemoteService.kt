package com.example.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 *
 * @author wangzhichao
 * @since 2021/6/25
 */
class RemoteService : Service() {
    private var callback: ICallback? = null
    private val callbackCommand = Runnable{
        if (callback != null) {
            try {
                Thread.sleep(2000L)
                Log.d(TAG, "onResult: result=1, currThread=${Thread.currentThread().name}")
                callback!!.onResult(1)
            } catch (e: Exception) {
                Log.e(TAG, "onResult: ", e)
            }
        }
    }
    private val scheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(1)
    private val stub = object : IController.Stub() {
        override fun registerCallback(callback: ICallback?) {
            Log.d(TAG, "registerCallback: callback=$callback, currThread=${Thread.currentThread().name}")
            this@RemoteService.callback = callback
            // 2s 后回调 callback 方法
            scheduledThreadPoolExecutor.schedule(callbackCommand, 2L, TimeUnit.SECONDS)
        }

        override fun transIn(state: State?): Int {
            Log.d(TAG, "transIn: state.value=${state?.value}, currThread=${Thread.currentThread().name}")
            val newValue = 2
            Log.d(TAG, "transIn: set value to $newValue")
            state?.value = newValue
            return 1
        }

        override fun transOut(state: State?): Int {
            Log.d(TAG, "transOut: state.value=${state?.value}, currThread=${Thread.currentThread().name}")
            val newValue = 2
            Log.d(TAG, "transOut: set value to $newValue")
            state?.value = newValue
            return 2
        }

        override fun transInOut(state: State?): Int {
            Log.d(TAG, "transInOut: state.value=${state?.value}, currThread=${Thread.currentThread().name}")
            val newValue = 2
            Log.d(TAG, "transInOut: set value to $newValue")
            state?.value = newValue
            return 3
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: currThread=${Thread.currentThread().name}")
        return stub.asBinder()
    }

    companion object {
        private const val TAG = "RemoteService"
    }
}