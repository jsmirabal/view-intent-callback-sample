package com.jsmirabal.viewintentsample.common.viewintent.throttling

import com.jsmirabal.viewintentsample.common.viewintent.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentSender
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

internal class ThrottleLastIntentCallback(
    private val callback: ViewIntentSender,
    private val throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
) : ViewIntentSender {

    private val lastInvocationMap = mutableMapOf<String, TimerTask?>()

    override operator fun invoke(intent: ViewIntent) {
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
