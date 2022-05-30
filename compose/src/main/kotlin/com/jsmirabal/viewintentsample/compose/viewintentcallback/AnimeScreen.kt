package com.jsmirabal.viewintentsample.compose.viewintentcallback

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.jsmirabal.viewintentsample.common.viewintentcallback.mvvm.ViewIntentCallback
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_FIRST
import com.jsmirabal.viewintentsample.common.viewintentcallback.throttling.ViewIntentThrottling.Type.THROTTLE_LAST
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.LoadAnimes
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.SearchAnime
import com.jsmirabal.viewintentsample.compose.viewintentcallback.AnimeViewIntent.SelectAnime

@Composable
fun AnimeScreen(
    uiState: State<AnimeViewState>,
    sender: ViewIntentCallback.Sender<AnimeViewIntent>
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    ) {
        TextField(
            value = "Anime Search",
            onValueChange = {
                sender.send(
                    intent = SearchAnime(it),
                    throttlingType = THROTTLE_LAST
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
        Button(
            onClick = {
                sender.send(
                    intent = SelectAnime(101),
                    throttlingType = THROTTLE_FIRST
                )
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(text = "Save Anime")
        }
    }

    LaunchedEffect(key1 = true) {
        sender.send(LoadAnimes)
    }
}