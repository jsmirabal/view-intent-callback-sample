package com.jsmirabal.viewintentsample.compose.viewintentcallback

import androidx.lifecycle.ViewModel
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AnimeViewModel(
    onViewIntent: ViewIntentCallback.Receiver<AnimeViewIntent>,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _animeViewState = MutableStateFlow<AnimeViewState>(AnimeViewState.Initial)
    val animeViewState: StateFlow<AnimeViewState> = _animeViewState

    init {
        onViewIntent(::receive)
    }

    private fun receive(intent: AnimeViewIntent) {
        when (intent) {
            AnimeViewIntent.LoadAnimes -> loadAnimeList()
            is AnimeViewIntent.SearchAnime -> searchAnime(intent.animeName)
            is AnimeViewIntent.SelectAnime -> onAnimeSelected(intent.animeId)
        }
    }

    private fun loadAnimeList() {
        fetchAnimeListUseCase().either(
            success = { _animeViewState.tryEmit(AnimeViewState.ShowAnimeList(it)) },
            error = { _animeViewState.tryEmit(AnimeViewState.ShowError(it)) }
        )
    }

    private fun searchAnime(animeName: String) {
        searchAnimeUseCase(animeName).either(
            success = { _animeViewState.tryEmit(AnimeViewState.ShowSearchView(it)) },
            error = { _animeViewState.tryEmit(AnimeViewState.ShowError(it)) }
        )
    }

    private fun onAnimeSelected(animeId: Int) {
        saveAnimeUseCase(animeId)
    }
}

sealed interface AnimeViewState {
    object Initial : AnimeViewState
    data class ShowAnimeList(val result: AnimeResult) : AnimeViewState
    data class ShowSearchView(val result: AnimeResult) : AnimeViewState
    data class ShowError(val error: AnimeError) : AnimeViewState
}

sealed interface AnimeViewIntent : ViewIntent {
    object LoadAnimes : AnimeViewIntent
    data class SearchAnime(val animeName: String) : AnimeViewIntent
    data class SelectAnime(val animeId: Int) : AnimeViewIntent
}
