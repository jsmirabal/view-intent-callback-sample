package com.jsmirabal.viewintentsample.mvp.flow

import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import kotlinx.coroutines.flow.Flow

interface AnimeActivityContract {

    interface View {
        fun getAnimeSaveButtonClicks(): Flow<Int>
        fun getAnimeSearchInputs(): Flow<String>
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter {
        fun start()
        fun stop()
    }
}