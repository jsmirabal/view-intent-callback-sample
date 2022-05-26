package com.jsmirabal.viewintentsample.mvp.rx

import android.app.Activity
import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.jsmirabal.viewintentsample.common.data.AnimeError
import com.jsmirabal.viewintentsample.common.data.AnimeResult
import com.jsmirabal.viewintentsample.mvp.databinding.ActivityAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@AndroidEntryPoint
class AnimeActivity : Activity(), AnimeActivityContract.View {

    @Inject
    private lateinit var presenter: AnimeActivityContract.Presenter

    private val binding: ActivityAnimeBinding by lazy { ActivityAnimeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        presenter.start()
    }

    override fun getAnimeSaveButtonClicks(): Observable<Int> = binding.animeSaveButton.clicks().map { 101 }

    override fun getAnimeSearchInputs(): Observable<String> = binding.animeSearch.textChanges().map { it.toString() }

    override fun showAnimeList(result: AnimeResult) {
        // render anime list
    }

    override fun showSearchView(result: AnimeResult) {
        // render search view
    }

    override fun showError(error: AnimeError) {
        // show error dialog
    }
}
