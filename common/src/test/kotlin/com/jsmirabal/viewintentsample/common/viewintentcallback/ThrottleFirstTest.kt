package com.jsmirabal.viewintentsample.common.viewintentcallback

import io.mockk.called
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.jupiter.api.Test

private const val TEST_THROTTLE_TIME_MILLIS = 50L

internal class ThrottleFirstTest {

    private val throttling = ViewIntentThrottlingImpl<ViewIntent>()

    @Test
    fun `WHEN throttleFirst is called AND the intents are the same THEN only invoke the first one`() {
        val intent = mockk<ViewIntent>()
        val block1 = mockk<() -> Unit>()
        val block2 = mockk<() -> Unit>()
        val block3 = mockk<() -> Unit>()

        justRun { block1() }

        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block1)
        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block3)

        verifyAll {
            block1.invoke()
            block2 wasNot called
            block3 wasNot called
        }
    }

    @Test
    fun `WHEN throttleFirst is called AND the intents are different THEN invoke all`() {
        val (intent1, block1) = mockk<ViewIntent>() to mockk<() -> Unit>()
        val (intent2, block2) = mockk<ViewIntent>() to mockk<() -> Unit>()
        val (intent3, block3) = mockk<ViewIntent>() to mockk<() -> Unit>()

        justRun {
            block1()
            block2()
            block3()
        }

        throttling.throttleFirst(intent1, TEST_THROTTLE_TIME_MILLIS, block1)
        throttling.throttleFirst(intent2, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleFirst(intent3, TEST_THROTTLE_TIME_MILLIS, block3)

        verifyAll {
            block1.invoke()
            block2.invoke()
            block3.invoke()
        }
    }

    @Test
    fun `WHEN throttleFirst is called AND an intent is invoked after a throttled time THEN invoke two`() {
        val intent = mockk<ViewIntent>()
        val block1 = mockk<() -> Unit>()
        val block2 = mockk<() -> Unit>()
        val block3 = mockk<() -> Unit>()

        justRun {
            block1()
            block2()
        }

        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block1)
        Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)
        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleFirst(intent, TEST_THROTTLE_TIME_MILLIS, block3)

        verifyAll {
            block1.invoke()
            block2.invoke()
            block3 wasNot called
        }
    }
}
