package com.jsmirabal.viewintentsample.mvvm.traditional

import androidx.lifecycle.ViewModel
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AnimeViewModel(
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _animeViewState = MutableSharedFlow<AnimeViewState>()
    val animeViewState: SharedFlow<AnimeViewState> = _animeViewState

    fun loadAnimeList() {
        fetchAnimeListUseCase().either(
            success = { _animeViewState.tryEmit(AnimeViewState.ShowAnimeList(it)) },
            error = { _animeViewState.tryEmit(AnimeViewState.ShowError(it)) }
        )
    }

    fun searchAnime(animeName: String) {
        searchAnimeUseCase(animeName).either(
            success = { _animeViewState.tryEmit(AnimeViewState.ShowSearchView(it)) },
            error = { _animeViewState.tryEmit(AnimeViewState.ShowError(it)) }
        )
    }

    fun saveAnime(animeId: Int) {
        saveAnimeUseCase(animeId)
    }
}

sealed interface AnimeViewState {
    data class ShowAnimeList(val result: AnimeResult) : AnimeViewState
    data class ShowSearchView(val result: AnimeResult) : AnimeViewState
    data class ShowError(val error: AnimeError) : AnimeViewState
}