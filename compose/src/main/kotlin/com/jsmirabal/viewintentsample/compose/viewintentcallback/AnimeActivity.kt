package com.jsmirabal.viewintentsample.compose.viewintentcallback

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import com.jsmirabal.viewintentsample.common.viewintentcallback.mvvm.ViewIntentCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity() {

    private val viewModel: AnimeViewModel by viewModels()

    @Inject
    lateinit var sender: ViewIntentCallback.Sender<AnimeViewIntent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimeScreen(
                uiState = viewModel.animeViewState.collectAsState(),
                sender = sender
            )
        }
    }
}