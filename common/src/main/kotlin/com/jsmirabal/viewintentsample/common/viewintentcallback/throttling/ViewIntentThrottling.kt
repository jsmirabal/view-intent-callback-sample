package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender

interface ThrottleFirst
interface ThrottleLast

internal const val DEFAULT_THROTTLE_TIME_MILLIS = 300L

fun <T : ViewIntent> throttle(
    callback: ViewIntentSender<T>,
    throttleTime: Long = DEFAULT_THROTTLE_TIME_MILLIS
): ViewIntentSender<T> = ThrottleIntentCallback(callback, throttleTime)
