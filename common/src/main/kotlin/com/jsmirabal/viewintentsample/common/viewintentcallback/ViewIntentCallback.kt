package com.jsmirabal.viewintentsample.common.viewintentcallback

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.NO_THROTTLING
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_LAST

interface ViewIntent

typealias ViewIntentBinder<T> = (T) -> Unit

interface ViewIntentCallback {

    interface Sender<T : ViewIntent> {
        fun send(
            intent: T,
            throttlingType: ViewIntentThrottling.Type = NO_THROTTLING,
            throttleTimeInMillis: Long = DEFAULT_THROTTLE_TIME_MILLIS
        )
    }

    interface Receiver<T : ViewIntent> {
        operator fun invoke(receive: ViewIntentBinder<T>)
    }
}

class ViewIntentCallbackImpl<T : ViewIntent> :
    ViewIntentCallback.Sender<T>,
    ViewIntentCallback.Receiver<T>,
    ViewIntentThrottling<T> by ViewIntentThrottlingImpl() {

    private var _send: ViewIntentBinder<T> = { }

    override operator fun invoke(receive: ViewIntentBinder<T>) {
        _send = receive
    }

    override fun send(
        intent: T,
        throttlingType: ViewIntentThrottling.Type,
        throttleTimeInMillis: Long
    ) {
        when (throttlingType) {
            THROTTLE_FIRST -> throttleFirst(intent, throttleTimeInMillis) { _send(intent) }
            THROTTLE_LAST -> throttleLast(intent, throttleTimeInMillis) { _send(intent) }
            NO_THROTTLING -> _send(intent)
        }
    }
}
