package com.jsmirabal.viewintentsample.compose.traditional

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity() {

    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimeScreen(
                uiState = viewModel.animeViewState.collectAsState(),
                onInitialState = { viewModel.loadAnimeList() },
                onAnimeSearchTextChanged = { viewModel.searchAnime(it) },
                onAnimeSelected = { viewModel.onAnimeSelected(it) }
            )
        }
    }
}