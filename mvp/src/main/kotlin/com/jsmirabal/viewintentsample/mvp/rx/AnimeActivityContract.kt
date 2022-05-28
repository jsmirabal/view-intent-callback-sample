package com.jsmirabal.viewintentsample.mvp.rx

import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import io.reactivex.rxjava3.core.Observable

interface AnimeActivityContract {

    interface View {
        fun getAnimeSaveButtonClicks(): Observable<Int>
        fun getAnimeSearchInputs(): Observable<String>
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter {
        fun start()
        fun stop()
    }
}