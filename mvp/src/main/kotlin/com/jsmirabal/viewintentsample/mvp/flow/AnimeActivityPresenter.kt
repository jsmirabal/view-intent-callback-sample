package com.jsmirabal.viewintentsample.mvp.flow

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@FlowPreview
class AnimeActivityPresenter @Inject constructor(
    private val view: AnimeActivityContract.View,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : AnimeActivityContract.Presenter {

    private val scope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    override fun start() {
        observeSaveAnimeButtonClicks()
        observeAnimeSearchInputs()
        fetchAnimes()
    }

    override fun stop() {
        scope.cancel()
    }

    private fun observeSaveAnimeButtonClicks() {
        scope.launch {
            view.getAnimeSaveButtonClicks()
                .debounce(timeoutMillis = 200)
                .collect { saveAnime(it) }
        }
    }

    private fun observeAnimeSearchInputs() {
        scope.launch {
            view.getAnimeSearchInputs()
                .debounce(timeoutMillis = 200)
                .collectLatest { searchAnime(it) }
        }
    }

    private fun fetchAnimes() {
        fetchAnimeListUseCase().either(
            success = { view.showAnimeList(it) },
            error = { view.showError(it) }
        )
    }

    private fun searchAnime(animeName: String) {
        searchAnimeUseCase(animeName).either(
            success = { view.showSearchView(it) },
            error = { view.showError(it) }
        )
    }

    private fun saveAnime(animeId: Int) {
        saveAnimeUseCase(animeId)
    }
}
