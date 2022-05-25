package com.jsmirabal.viewintentsample.mvp.simplified

import android.app.Activity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jsmirabal.viewintentsample.common.data.AnimeError
import com.jsmirabal.viewintentsample.common.data.AnimeResult
import com.jsmirabal.viewintentsample.common.viewintent.ViewIntentSender
import com.jsmirabal.viewintentsample.mvp.databinding.ActivityAnimeBinding
import com.jsmirabal.viewintentsample.mvp.simplified.AnimeActivityContract.Intent.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimeActivity : Activity(), AnimeActivityContract.View {

    @Inject
    private lateinit var presenter: AnimeActivityContract.Presenter

    private val binding: ActivityAnimeBinding by lazy { ActivityAnimeBinding.inflate(layoutInflater) }

    override var sender: ViewIntentSender = {  }

    override fun onIntent(sender: ViewIntentSender) {
        this.sender = sender
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        sender(LoadAnimes)
        initListeners()
    }

    private fun initListeners() {
        binding.animeSearch.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->  sender(SearchAnime(text.toString()))}
        )
        binding.animeSaveButton.setOnClickListener { sender(SelectAnime(animeId = 101)) }
    }

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