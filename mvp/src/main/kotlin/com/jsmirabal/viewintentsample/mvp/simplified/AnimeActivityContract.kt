package com.jsmirabal.viewintentsample.mvp.simplified

import com.jsmirabal.viewintentsample.common.data.AnimeError
import com.jsmirabal.viewintentsample.common.data.AnimeResult
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntent
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentCallback

interface AnimeActivityContract {

    sealed interface Intent : ViewIntent {
        object LoadAnimes : Intent
        data class SearchAnime(val animeName: String) : Intent
        data class SelectAnime(val animeId: Int) : Intent
    }

    interface View : ViewIntentCallback {
        fun showAnimeList(result: AnimeResult)
        fun showSearchView(result: AnimeResult)
        fun showError(error: AnimeError)
    }

    interface Presenter
}