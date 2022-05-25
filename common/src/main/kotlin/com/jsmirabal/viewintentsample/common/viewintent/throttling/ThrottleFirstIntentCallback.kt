package com.jsmirabal.viewintentsample.common.viewintent.throttling

import com.jsmirabal.viewintentsample.common.viewintent.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentSender

internal class ThrottleFirstIntentCallback(
    private val callback: ViewIntentSender,
    private val throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
) : ViewIntentSender {

    private val lastInvocationMap = mutableMapOf<ViewIntent, Long>()

    override operator fun invoke(intent: ViewIntent) {
        val lastInvocation = lastInvocationMap[intent] ?: 0L

        if (lastInvocation == 0L || System.currentTimeMillis() - lastInvocation > throttleTimeInMillis) {
            callback(intent)
            lastInvocationMap[intent] = System.currentTimeMillis()
        }
    }
}
