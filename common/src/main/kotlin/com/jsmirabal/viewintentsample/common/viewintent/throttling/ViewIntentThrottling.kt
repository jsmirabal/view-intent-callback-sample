package com.jsmirabal.viewintentsample.common.viewintent.throttling

import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentSender

interface ThrottleFirst
interface ThrottleLast

internal const val DEFAULT_THROTTLE_TIME_MILLIS = 300L

fun throttle(
    callback: ViewIntentSender,
    throttleTime: Long = DEFAULT_THROTTLE_TIME_MILLIS
): ViewIntentSender = ThrottleIntentCallback(callback, throttleTime)
