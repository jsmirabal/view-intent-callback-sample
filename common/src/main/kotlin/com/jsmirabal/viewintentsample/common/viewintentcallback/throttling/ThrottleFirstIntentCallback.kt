package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender

internal class ThrottleFirstIntentCallback<T : ViewIntent>(
    private val callback: ViewIntentSender<T>,
    private val throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
) : ViewIntentSender<T> {

    private val lastInvocationMap = mutableMapOf<T, Long>()

    override operator fun invoke(intent: T) {
        val lastInvocation = lastInvocationMap[intent] ?: 0L

        if (lastInvocation == 0L || System.currentTimeMillis() - lastInvocation > throttleTimeInMillis) {
            callback(intent)
            lastInvocationMap[intent] = System.currentTimeMillis()
        }
    }
}
