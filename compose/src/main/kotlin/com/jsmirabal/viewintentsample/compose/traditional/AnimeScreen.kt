package com.jsmirabal.viewintentsample.compose.traditional

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

@Composable
fun AnimeScreen(
    uiState: State<AnimeViewState>,
    onInitialState: suspend () -> Unit,
    onAnimeSearchTextChanged: (String) -> Unit,
    onAnimeSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    ) {
        TextField(
            value = "Anime Search",
            onValueChange = { onAnimeSearchTextChanged(it) },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
        Button(
            onClick = { onAnimeSelected(101) },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(text = "Save Anime")
        }
    }

    LaunchedEffect(key1 = true) {
        onInitialState()
    }
}