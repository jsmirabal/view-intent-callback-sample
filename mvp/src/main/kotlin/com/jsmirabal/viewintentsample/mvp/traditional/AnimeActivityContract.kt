package com.jsmirabal.viewintentsample.mvp.traditional

import com.jsmirabal.viewintentsample.common.data.AnimeError
import com.jsmirabal.viewintentsample.common.data.AnimeResult

interface AnimeActivityContract {

    interface View {
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter {
        fun start()
        fun searchAnime(animeName: String)
        fun onAnimeSelected(animeId: Int)
    }
}