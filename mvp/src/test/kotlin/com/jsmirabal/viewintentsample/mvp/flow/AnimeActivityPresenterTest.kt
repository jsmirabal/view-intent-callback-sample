package com.jsmirabal.viewintentsample.mvp.flow

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@FlowPreview
@ExperimentalCoroutinesApi
internal class AnimeActivityPresenterTest {

    private val view = mockk<AnimeActivityContract.View>()
    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val presenter = spyk(
        AnimeActivityPresenter(
            view,
            fetchAnimeListUseCase,
            saveAnimeUseCase,
            searchAnimeUseCase
        ),
        recordPrivateCalls = true
    ).also { spy ->
        every { spy getProperty "scope" } returns testScope
    }

    @BeforeEach
    fun setup() {
        every { fetchAnimeListUseCase() } returns mockk()
        every { saveAnimeUseCase(any()) } returns mockk()
        every { searchAnimeUseCase(any()) } returns mockk()

        every { view.getAnimeSaveButtonClicks() } returns emptyFlow()
        every { view.getAnimeSearchInputs() } returns emptyFlow()
    }

    @Test
    fun `WHEN start is called THEN observe anime save button clicks`() = runTest {
        val flow = MutableSharedFlow<Int>()
        val animeId = 101

        every { view.getAnimeSaveButtonClicks() } returns flow

        presenter.start()
        testScope.runCurrent()

        flow.emit(animeId)
        testScope.advanceUntilIdle()

        verify { saveAnimeUseCase(animeId) }
    }

    @Test
    fun `WHEN start is called THEN observe anime search inputs`() = runTest {
        val flow = MutableSharedFlow<String>()
        val animeName = "Naruto"

        every { view.getAnimeSearchInputs() } returns flow

        presenter.start()
        testScope.runCurrent()

        flow.emit(animeName)
        testScope.advanceUntilIdle()

        verify { searchAnimeUseCase(animeName) }
    }

    @Test
    fun `WHEN start is called THEN fetch anime list`() = runTest {
        presenter.start()
        testScope.runCurrent()

        verify { fetchAnimeListUseCase() }
    }
}