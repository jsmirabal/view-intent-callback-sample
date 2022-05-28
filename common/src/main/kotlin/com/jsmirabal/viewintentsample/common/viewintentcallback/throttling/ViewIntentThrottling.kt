package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

private const val DEFAULT_THROTTLE_TIME_MILLIS = 200L

interface ViewIntentThrottling<T : ViewIntent> {

    fun throttleFirst(
        intent: T,
        throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS,
        block: () -> Unit
    )

    fun throttleLast(
        intent: T,
        throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS,
        block: () -> Unit
    )
}

class ViewIntentThrottlingImpl<T : ViewIntent> : ViewIntentThrottling<T> {

    private val triggerThrottleFirst by lazy { ThrottleFirst<T>() }
    private val triggerThrottleLast by lazy { ThrottleLast<T>() }

    override fun throttleFirst(intent: T, throttleTimeInMillis: Long, block: () -> Unit) {
        triggerThrottleFirst(intent, throttleTimeInMillis, block)
    }

    override fun throttleLast(intent: T, throttleTimeInMillis: Long, block: () -> Unit) {
        triggerThrottleLast(intent, throttleTimeInMillis, block)
    }
}

private class ThrottleFirst<in T : ViewIntent> {

    private val lastInvocationMap = mutableMapOf<T, Long>()

    operator fun invoke(intent: T, throttleTimeInMillis: Long, block: () -> Unit) {
        val lastInvocation: Long = lastInvocationMap[intent] ?: 0L
        val currentInvocation: Long = System.currentTimeMillis()
        val isInvokedAfterDelay = (currentInvocation - lastInvocation) > throttleTimeInMillis

        if (isInvokedAfterDelay) {
            block()
            lastInvocationMap[intent] = currentInvocation
        }
    }
}

private class ThrottleLast<in T : ViewIntent> {

    private val lastInvocationMap = mutableMapOf<String, TimerTask>()

    operator fun invoke(intent: T, throttleTimeInMillis: Long, block: () -> Unit) {
        val intentName = intent::class.simpleName.orEmpty()
        val currentTimerTask = lastInvocationMap[intentName]

        currentTimerTask?.cancel()

        Timer()
            .schedule(delay = throttleTimeInMillis) {
                block()
                lastInvocationMap.remove(intentName)
            }
            .also { lastInvocationMap[intentName] = it }
    }
}
