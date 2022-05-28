package com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.test

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling
import io.mockk.every

object ViewIntentThrottlingTestUtil {

    inline fun <reified T : ViewIntent> mockViewIntentThrottling(mock: ViewIntentThrottling<T>) {
        every {
            mock.throttleFirst(any(), any(), captureLambda())
        } answers {
            lambda<() -> Unit>().captured.invoke()
        }

        every {
            mock.throttleLast(any(), any(), captureLambda())
        } answers {
            lambda<() -> Unit>().captured.invoke()
        }
    }
}