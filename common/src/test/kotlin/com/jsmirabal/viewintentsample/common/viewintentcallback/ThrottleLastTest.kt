package com.jsmirabal.viewintentsample.common.viewintentcallback

import io.mockk.called
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.jupiter.api.Test

private const val TEST_THROTTLE_TIME_MILLIS = 50L

internal class ThrottleLastTest {

    private val throttling = ViewIntentThrottlingImpl<ViewIntent>()

    @Test
    fun `WHEN throttleLast is called AND several similar intents are sent THEN invoke the last one`() {
        val (intent1, block1) = TestViewIntent1("1") to mockk<() -> Unit>()
        val (intent2, block2) = TestViewIntent1("2") to mockk<() -> Unit>()
        val (intent3, block3) = TestViewIntent1("3") to mockk<() -> Unit>()

        throttling.throttleLast(intent1, TEST_THROTTLE_TIME_MILLIS, block1)
        throttling.throttleLast(intent2, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleLast(intent3, TEST_THROTTLE_TIME_MILLIS, block3)
        Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)

        verifyAll {
            block1 wasNot called
            block2 wasNot called
            block3.invoke()
        }
    }

    @Test
    fun `WHEN throttleLast AND several different intent types are invoked THEN invoke all`() {
        val (intent1, block1) = TestViewIntent1("1") to mockk<() -> Unit>()
        val (intent2, block2) = TestViewIntent2("2") to mockk<() -> Unit>()
        val (intent3, block3) = TestViewIntent3("3") to mockk<() -> Unit>()

        throttling.throttleLast(intent1, TEST_THROTTLE_TIME_MILLIS, block1)
        throttling.throttleLast(intent2, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleLast(intent3, TEST_THROTTLE_TIME_MILLIS, block3)
        Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)

        verifyAll {
            block1.invoke()
            block2.invoke()
            block3.invoke()
        }
    }

    @Test
    fun `WHEN throttleLast is called AND an intent is sent after a throttled time THEN invoke both`() {
        val (intent1, block1) = TestViewIntent1("1") to mockk<() -> Unit>()
        val (intent2, block2) = TestViewIntent1("2") to mockk<() -> Unit>()
        val (intent3, block3) = TestViewIntent1("3") to mockk<() -> Unit>()

        throttling.throttleLast(intent1, TEST_THROTTLE_TIME_MILLIS, block1)
        Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)
        throttling.throttleLast(intent2, TEST_THROTTLE_TIME_MILLIS, block2)
        throttling.throttleLast(intent3, TEST_THROTTLE_TIME_MILLIS, block3)
        Thread.sleep(TEST_THROTTLE_TIME_MILLIS + 10)

        verifyAll {
            block1.invoke()
            block2 wasNot called
            block3.invoke()
        }
    }

    data class TestViewIntent1(val value: String) : ViewIntent
    data class TestViewIntent2(val value: String) : ViewIntent
    data class TestViewIntent3(val value: String) : ViewIntent
}
