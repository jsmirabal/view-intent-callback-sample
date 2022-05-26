package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender

internal class ThrottleIntentCallback<in T : ViewIntent>(
    private val callback: ViewIntentSender<T>,
    throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
) : ViewIntentSender<T> {

    private val throttleFirstCallback = ThrottleFirstIntentCallback(callback, throttleTimeInMillis)
    private val throttleLastCallback = ThrottleLastIntentCallback(callback, throttleTimeInMillis)

    override operator fun invoke(intent: T) {
        when (intent) {
            is ThrottleFirst -> throttleFirstCallback(intent)
            is ThrottleLast -> throttleLastCallback(intent)
            else -> callback(intent)
        }
    }
}
