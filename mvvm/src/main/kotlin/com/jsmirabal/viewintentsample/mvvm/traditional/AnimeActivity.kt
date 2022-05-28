package com.jsmirabal.viewintentsample.mvvm.traditional

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult
import com.jsmirabal.viewintentsample.mvvm.databinding.ActivityAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity() {

    private val viewModel: AnimeViewModel by viewModels()

    private val binding: ActivityAnimeBinding by lazy { ActivityAnimeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadAnimeList()

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
            onTextChanged = { text, _, _, _ -> viewModel.searchAnime(text.toString()) }
        )
        binding.animeSaveButton.setOnClickListener { viewModel.onAnimeSelected(animeId = 101) }
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