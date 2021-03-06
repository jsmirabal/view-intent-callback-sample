package com.jsmirabal.viewintentsample.mvp.viewintentcallback

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentCallback
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.LoadAnimes
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SearchAnime
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SaveAnime
import javax.inject.Inject

class AnimeActivityPresenter @Inject constructor(
    onViewIntent: ViewIntentCallback.Receiver<AnimeActivityContract.Intent>,
    private val view: AnimeActivityContract.View,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : AnimeActivityContract.Presenter {

    init {
        onViewIntent(::receive)
    }

    private fun receive(intent: AnimeActivityContract.Intent) {
        when (intent) {
            LoadAnimes -> fetchAnimes()
            is SearchAnime -> searchAnime(intent.animeName)
            is SaveAnime -> saveAnime(intent.animeId)
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