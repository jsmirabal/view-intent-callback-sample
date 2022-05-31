package com.jsmirabal.viewintentsample.mvp.viewintentcallback

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.common.viewintentcallback.ViewIntentCallback
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_LAST
import com.jsmirabal.viewintentsample.mvp.databinding.ActivityAnimeBinding
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.LoadAnimes
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SearchAnime
import com.jsmirabal.viewintentsample.mvp.viewintentcallback.AnimeActivityContract.Intent.SelectAnime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity(), AnimeActivityContract.View {

    @Inject
    lateinit var presenter: AnimeActivityContract.Presenter

    private val binding: ActivityAnimeBinding by lazy { ActivityAnimeBinding.inflate(layoutInflater) }

    @Inject
    lateinit var sender: ViewIntentCallback.Sender<AnimeActivityContract.Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        sender.send(LoadAnimes)
        initListeners()
    }

    private fun initListeners() {
        binding.animeSearch.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                sender.send(
                    intent = SearchAnime(text.toString()),
                    throttlingType = THROTTLE_LAST
                )
            }
        )
        binding.animeSaveButton.setOnClickListener {
            sender.send(
                intent = SelectAnime(animeId = 101),
                throttlingType = THROTTLE_FIRST
            )
        }
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