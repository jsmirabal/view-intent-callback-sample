package com.jsmirabal.viewintentsample.mvp.traditional

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import javax.inject.Inject

class AnimeActivityPresenter @Inject constructor(
    private val view: AnimeActivityContract.View,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : AnimeActivityContract.Presenter {

    override fun start() {
        fetchAnimeListUseCase().either(
            success = { view.showAnimeList(it) },
            error = { view.showError(it) }
        )
    }

    override fun searchAnime(animeName: String) {
        searchAnimeUseCase(animeName).either(
            success = { view.showSearchView(it) },
            error = { view.showError(it) }
        )
    }

    override fun saveAnime(animeId: Int) {
        saveAnimeUseCase(animeId)
    }
}