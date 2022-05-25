package com.jsmirabal.viewintentsample.common.viewintent.throttling

import com.jsmirabal.viewintentsample.common.viewintent.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentSender

internal class ThrottleIntentCallback(
    private val callback: ViewIntentSender,
    throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS,
    private val throttleFirstCallback: ThrottleFirstIntentCallback = ThrottleFirstIntentCallback(callback, throttleTimeInMillis),
    private val throttleLastCallback: ThrottleLastIntentCallback = ThrottleLastIntentCallback(callback, throttleTimeInMillis)
) : ViewIntentSender {

    override operator fun invoke(intent: ViewIntent) {
        when (intent) {
            is ThrottleFirst -> throttleFirstCallback(intent)
            is ThrottleLast -> throttleLastCallback(intent)
            else -> callback(intent)
        }
    }
}
