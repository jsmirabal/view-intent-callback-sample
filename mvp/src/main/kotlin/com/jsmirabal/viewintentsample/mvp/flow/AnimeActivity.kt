package com.jsmirabal.viewintentsample.mvp.flow

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.jsmirabal.viewintentsample.common.data.AnimeError
import com.jsmirabal.viewintentsample.common.data.AnimeResult
import com.jsmirabal.viewintentsample.mvp.databinding.ActivityAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
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

    override fun getAnimeSaveButtonClicks(): Flow<Int> =
        binding.animeSaveButton.clicksFlow().map { 101 }

    override fun getAnimeSearchInputs(): Flow<String> =
        binding.animeSearch.textChangesFlow().map { it.toString() }

    override fun showAnimeList(result: AnimeResult) {
        // render anime list
    }

    override fun showSearchView(result: AnimeResult) {
        // render search view
    }

    override fun showError(error: AnimeError) {
        // show error dialog
    }

    private fun View.clicksFlow(): Flow<Unit> =
        MutableSharedFlow<Unit>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_LATEST
        ).also { emitter ->
            setOnClickListener { emitter.tryEmit(Unit) }

            emitter.onCompletion { setOnClickListener(null) }
        }

    private fun TextView.textChangesFlow(): Flow<CharSequence> =
        MutableSharedFlow<CharSequence>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        ).also { emitter ->
            addTextChangedListener(
                onTextChanged = { text, _, _, _ -> emitter.tryEmit(text!!) }
            )

            emitter.onCompletion { removeTextChangedListener(null) }
        }
}
