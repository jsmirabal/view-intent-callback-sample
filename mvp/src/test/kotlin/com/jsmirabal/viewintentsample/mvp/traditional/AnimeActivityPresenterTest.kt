package com.jsmirabal.viewintentsample.mvp.traditional

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AnimeActivityPresenterTest {

    private val view = mockk<AnimeActivityContract.View>()
    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()

    private val presenter = AnimeActivityPresenter(
        view,
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

    @Test
    fun `WHEN saveAnime is called THEN save anime`() {
        val animeId = 101

        presenter.saveAnime(animeId)

        verify { saveAnimeUseCase(animeId) }
    }

    @Test
    fun `WHEN searchAnime is called THEN search for anime`() {
        val animeName = "Naruto"

        presenter.searchAnime(animeName)

        verify { searchAnimeUseCase(animeName) }
    }

    @Test
    fun `WHEN start is called THEN fetch anime list`() {
        presenter.start()

        verify { fetchAnimeListUseCase() }
    }
}