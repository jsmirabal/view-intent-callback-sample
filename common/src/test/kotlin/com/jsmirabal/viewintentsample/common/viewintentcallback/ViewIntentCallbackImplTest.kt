package com.jsmirabal.viewintentsample.common.viewintentcallback

import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.NO_THROTTLING
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentThrottling.Type.THROTTLE_LAST
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private const val TEST_THROTTLE_TIME_MILLIS = 50L

internal class ViewIntentCallbackImplTest {

    private val viewIntentCallback = ViewIntentCallbackImpl<ViewIntent>()

    @ParameterizedTest
    @EnumSource(ViewIntentThrottling.Type::class)
    fun `WHEN send is called THEN invoke the lambda based on the throttling type`(throttlingType: ViewIntentThrottling.Type) {
        val sender = mockk<(ViewIntent) -> Unit>()
        val intent = mockk<ViewIntent>()

        justRun { sender.invoke(intent) }

        viewIntentCallback.invoke(sender)
        viewIntentCallback.send(intent, throttlingType, TEST_THROTTLE_TIME_MILLIS)

        when (throttlingType) {
            THROTTLE_FIRST -> verify { sender.invoke(intent) }
            THROTTLE_LAST -> {
                Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)
                verify { sender.invoke(intent) }
            }
            NO_THROTTLING -> verify { sender.invoke(intent) }
        }
    }
}
