package com.jsmirabal.viewintentsample.mvp.traditional

import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult

interface AnimeActivityContract {

    interface View {
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter {
        fun start()
        fun searchAnime(animeName: String)
        fun saveAnime(animeId: Int)
    }
}