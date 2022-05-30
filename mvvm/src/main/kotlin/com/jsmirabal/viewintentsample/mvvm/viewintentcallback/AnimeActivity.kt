package com.jsmirabal.viewintentsample.mvvm.viewintentcallback

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.common.viewintentcallback.mvvm.ViewIntentCallback
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_LAST
import com.jsmirabal.viewintentsample.mvvm.databinding.ActivityAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity() {

    private val viewModel: AnimeViewModel by viewModels()

    private val binding: ActivityAnimeBinding by lazy { ActivityAnimeBinding.inflate(layoutInflater) }

    @Inject
    lateinit var sender: ViewIntentCallback.Sender<AnimeViewIntent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sender.send(AnimeViewIntent.LoadAnimes)

                viewModel.animeViewState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: AnimeViewState) {
        when (state) {
            is AnimeViewState.ShowAnimeList -> showAnimeList(state.result)
            is AnimeViewState.ShowSearchView -> showSearchView(state.result)
            is AnimeViewState.ShowError -> showError(state.error)
        }
    }

    private fun initListeners() {
        binding.animeSearch.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                sender.send(
                    intent = AnimeViewIntent.SearchAnime(text.toString()),
                    throttlingType = THROTTLE_LAST
                )
            }
        )
        binding.animeSaveButton.setOnClickListener {
            sender.send(
                intent = AnimeViewIntent.SelectAnime(animeId = 101),
                throttlingType = THROTTLE_FIRST
            )
        }
    }

    private fun showAnimeList(result: AnimeResult) {
        // render anime list
    }

    private fun showSearchView(result: AnimeResult) {
        // render search view
    }

    private fun showError(error: AnimeError) {
        // show error dialog
    }
}