package com.jsmirabal.viewintentsample.mvp.viewintentcallback

import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntent

interface AnimeActivityContract {

    sealed interface Intent : ViewIntent {
        object LoadAnimes : Intent
        data class SearchAnime(val animeName: String) : Intent
        data class SaveAnime(val animeId: Int) : Intent
    }

    interface View {
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter
}