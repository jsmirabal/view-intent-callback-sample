package com.jsmirabal.viewintentsample.compose.viewintentcallback

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentBinder
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentCallback
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.LoadAnimes
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.SearchAnime
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.SaveAnime
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class AnimeViewModelTest {

    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()
    private val receiver = mockk<ViewIntentCallback.Receiver<AnimeViewIntent>>()

    private fun runViewModel() = AnimeViewModel(
        receiver,
        fetchAnimeListUseCase,
        saveAnimeUseCase,
        searchAnimeUseCase
    )

    @BeforeEach
    fun setup() {
        every { fetchAnimeListUseCase() } returns mockk()
        every { saveAnimeUseCase(any()) } returns mockk()
        every { searchAnimeUseCase(any()) } returns mockk()
    }

    @ParameterizedTest
    @EnumSource(AnimeViewIntentScenario::class)
    fun `WHEN a view intent is received THEN verify the expected outcome`(scenario: AnimeViewIntentScenario) {
        val senderSlot = slot<ViewIntentBinder<AnimeViewIntent>>()

        justRun { receiver(capture(senderSlot)) }

        runViewModel()

        senderSlot.captured(scenario.intent)

        verify {
            when (scenario.intent) {
                LoadAnimes -> fetchAnimeListUseCase()
                is SearchAnime -> searchAnimeUseCase(scenario.intent.animeName)
                is SaveAnime -> saveAnimeUseCase(scenario.intent.animeId)
            }
        }
    }

    enum class AnimeViewIntentScenario(val intent: AnimeViewIntent) {
        LOAD_ANIMES(LoadAnimes),
        SEARCH_ANIME(SearchAnime(animeName = "")),
        SAVE_ANIME(SaveAnime(animeId = 101))
    }
}