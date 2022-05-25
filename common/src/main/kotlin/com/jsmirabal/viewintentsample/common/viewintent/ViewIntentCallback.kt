package com.jsmirabal.viewintentsample.common.viewintent

interface ViewIntent

typealias ViewIntentSender = (ViewIntent) -> Unit

interface ViewIntentCallback {
    var sender: ViewIntentSender

    fun onIntent(sender: ViewIntentSender)
}
