package com.jsmirabal.viewintentsample.common.viewintentcallback

import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.NO_THROTTLING
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_LAST
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottlingImpl

interface ViewIntent

typealias ViewIntentSender<T> = (T) -> Unit

interface ViewIntentCallback {

    interface Sender<T : ViewIntent> {
        fun send(intent: T, throttlingType: ViewIntentThrottling.Type = NO_THROTTLING)
    }

    interface Receiver<T : ViewIntent> {
        operator fun invoke(sender: ViewIntentSender<T>)
    }
}

class ViewIntentCallbackImpl<T : ViewIntent> :
    ViewIntentCallback.Sender<T>,
    ViewIntentCallback.Receiver<T>,
    ViewIntentThrottling<T> by ViewIntentThrottlingImpl() {

    private var sender: ViewIntentSender<T> = { }

    override operator fun invoke(sender: ViewIntentSender<T>) {
        this.sender = sender
    }

    override fun send(intent: T, throttlingType: ViewIntentThrottling.Type) {
        when (throttlingType) {
            THROTTLE_FIRST -> throttleFirst(intent) { sender(intent) }
            THROTTLE_LAST -> throttleLast(intent) { sender(intent) }
            NO_THROTTLING -> sender(intent)
        }
    }
}
