package com.jsmirabal.viewintentsample.mvp.viewintentcallback

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottlingImpl
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.LoadAnimes
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SearchAnime
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SelectAnime
import javax.inject.Inject

class AnimeActivityPresenter @Inject constructor(
    private val view: AnimeActivityContract.View,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase,
    throttlingDelegate: AnimeActivityThrottling = ViewIntentThrottlingImpl()
) : AnimeActivityContract.Presenter, AnimeActivityThrottling by throttlingDelegate {

    init {
        view.onIntent(::receive)
    }

    private fun receive(intent: AnimeActivityContract.Intent) {
        when (intent) {
            LoadAnimes -> fetchAnimes()
            is SearchAnime -> throttleLast(intent) { searchAnime(intent.animeName) }
            is SelectAnime -> throttleFirst(intent) { onAnimeSelected(intent.animeId) }
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

    private fun onAnimeSelected(animeId: Int) {
        saveAnimeUseCase(animeId)
    }
}