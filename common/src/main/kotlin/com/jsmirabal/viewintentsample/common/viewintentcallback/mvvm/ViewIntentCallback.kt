package com.jsmirabal.viewintentsample.common.viewintentcallback.mvvm

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender

interface ViewIntentCallback<T : ViewIntent> {

    interface Sender<T : ViewIntent> : ViewIntentCallback<T> {
        fun send(intent: T)
    }

    interface Receiver<T : ViewIntent> : ViewIntentCallback<T> {
        operator fun invoke(sender: ViewIntentSender<T>)
    }
}

class ViewIntentCallbackImpl<T : ViewIntent>
    : ViewIntentCallback.Sender<T>, ViewIntentCallback.Receiver<T> {

    private var sender: ViewIntentSender<T> = {  }

    override operator fun invoke(sender: ViewIntentSender<T>) {
        this.sender = sender
    }

    override fun send(intent: T) {
        sender(intent)
    }
}
