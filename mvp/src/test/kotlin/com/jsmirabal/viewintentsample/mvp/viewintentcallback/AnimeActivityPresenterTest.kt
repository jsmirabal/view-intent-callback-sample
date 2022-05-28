package com.jsmirabal.viewintentsample.mvp.viewintentcallback

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentSender
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.*
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class AnimeActivityPresenterTest : AnimeActivityThrottling by mockk() {

    private val view = mockk<AnimeActivityContract.View>()
    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()

    private fun runPresenter() = AnimeActivityPresenter(
        view,
        fetchAnimeListUseCase,
        saveAnimeUseCase,
        searchAnimeUseCase,
        throttlingDelegate = this
    )

    @BeforeEach
    fun setup() {
        every { fetchAnimeListUseCase() } returns mockk()
        every { saveAnimeUseCase(any()) } returns mockk()
        every { searchAnimeUseCase(any()) } returns mockk()

        mockIntentThrottling()
    }

    private fun mockIntentThrottling() {
        every {
            any<AnimeActivityContract.Intent>().throttleFirst(any(), captureLambda())
        } answers {
            lambda<() -> Unit>().captured.invoke()
        }

        every {
            any<AnimeActivityContract.Intent>().throttleLast(any(), captureLambda())
        } answers {
            lambda<() -> Unit>().captured.invoke()
        }
    }

    @ParameterizedTest
    @EnumSource(PresenterViewIntentScenario::class)
    fun `WHEN a view intent is received THEN verify the expected outcome`(scenario: PresenterViewIntentScenario) {
        val senderSlot = slot<ViewIntentSender<AnimeActivityContract.Intent>>()

        justRun { view.onIntent(capture(senderSlot)) }

        runPresenter()

        senderSlot.captured(scenario.intent)

        verify {
            when (scenario.intent) {
                LoadAnimes -> fetchAnimeListUseCase()
                is SearchAnime -> searchAnimeUseCase(scenario.intent.animeName)
                is SelectAnime -> saveAnimeUseCase(scenario.intent.animeId)
            }
        }
    }

    enum class PresenterViewIntentScenario(val intent: AnimeActivityContract.Intent) {
        LOAD_ANIMES(LoadAnimes),
        SEARCH_ANIME(SearchAnime(animeName = "")),
        SELECT_ANIME(SelectAnime(animeId = 101))
    }
}