package com.jsmirabal.viewintentsample.mvvm.traditional

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class AnimeViewModelTest {

    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()

    private val viewModel = AnimeViewModel(
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
    fun `WHEN onAnimeSelected is called THEN save anime`() {
        val animeId = 101

        viewModel.onAnimeSelected(animeId)

        verify { saveAnimeUseCase(animeId) }
    }

    @Test
    fun `WHEN searchAnime is called THEN search for anime`() {
        val animeName = "Naruto"

        viewModel.searchAnime(animeName)

        verify { searchAnimeUseCase(animeName) }
    }

    @Test
    fun `WHEN start is called THEN fetch anime list`() {
        viewModel.loadAnimeList()

        verify { fetchAnimeListUseCase() }
    }
}