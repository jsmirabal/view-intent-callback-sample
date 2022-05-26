package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

internal class ThrottleLastIntentCallback<T : ViewIntent>(
    private val callback: ViewIntentSender<T>,
    private val throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
) : ViewIntentSender<T> {

    private val lastInvocationMap = mutableMapOf<String, TimerTask?>()

    override operator fun invoke(intent: T) {
        val intentName: String = intent::class.simpleName.orEmpty()
        var timer: TimerTask? = lastInvocationMap[intentName]

        timer?.cancel()

        timer = Timer().schedule(delay = throttleTimeInMillis) {
            callback(intent)
            lastInvocationMap[intentName] = null
        }

        lastInvocationMap[intentName] = timer
    }
}
