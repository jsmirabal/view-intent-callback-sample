package com.jsmirabal.viewintentsample.common.viewintentcallback

interface ViewIntent

typealias ViewIntentSender<T> = (T) -> Unit

interface ViewIntentCallback<T : ViewIntent> {
    var sender: ViewIntentSender<T>

    fun onIntent(sender: ViewIntentSender<T>)
}
