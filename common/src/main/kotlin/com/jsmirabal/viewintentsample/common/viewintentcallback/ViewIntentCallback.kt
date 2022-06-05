package com.jsmirabal.viewintentsample.common.viewintentcallback

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.NO_THROTTLING
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_LAST

interface ViewIntent

interface ViewIntentCallback {

    interface Sender<T : ViewIntent> {
        fun send(
            intent: T,
            throttlingType: ViewIntentThrottling.Type = NO_THROTTLING,
            throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
        )
    }

    interface Receiver<T : ViewIntent> {
        operator fun invoke(sender: (T) -> Unit)
    }
}

class ViewIntentCallbackImpl<T : ViewIntent> :
    ViewIntentCallback.Sender<T>,
    ViewIntentCallback.Receiver<T>,
    ViewIntentThrottling<T> by ViewIntentThrottlingImpl() {

    private var sender: (T) -> Unit = { }

    override operator fun invoke(sender: (T) -> Unit) {
        this.sender = sender
    }

    override fun send(
        intent: T,
        throttlingType: ViewIntentThrottling.Type,
        throttleTimeInMillis: Long
    ) {
        when (throttlingType) {
            THROTTLE_FIRST -> throttleFirst(intent, throttleTimeInMillis) { sender(intent) }
            THROTTLE_LAST -> throttleLast(intent, throttleTimeInMillis) { sender(intent) }
            NO_THROTTLING -> sender(intent)
        }
    }
}
