package com.jsmirabal.viewintentsample.mvp.rx

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AnimeActivityPresenter @Inject constructor(
    private val view: AnimeActivityContract.View,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
    private val saveAnimeUseCase: SaveAnimeUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase
) : AnimeActivityContract.Presenter {

    private val disposables = CompositeDisposable()

    override fun start() {
        observeSaveAnimeButtonClicks()
        observeAnimeSearchInputs()
        fetchAnimes()
    }

    override fun stop() {
        disposables.clear()
    }

    private fun observeSaveAnimeButtonClicks() {
        view.getAnimeSaveButtonClicks()
            .throttleFirst(200, TimeUnit.MILLISECONDS)
            .subscribe { saveAnime(it) }
            .also { disposables.add(it) }
    }

    private fun observeAnimeSearchInputs() {
        view.getAnimeSearchInputs()
            .throttleLast(200, TimeUnit.MILLISECONDS)
            .subscribe { searchAnime(it) }
            .also { disposables.add(it) }
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
