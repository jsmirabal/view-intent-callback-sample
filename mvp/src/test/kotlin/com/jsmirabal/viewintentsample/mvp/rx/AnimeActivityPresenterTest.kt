package com.jsmirabal.viewintentsample.mvp.rx

import com.jsmirabal.viewintentsample.common.domain.usecase.FetchAnimeListUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SaveAnimeUseCase
import com.jsmirabal.viewintentsample.common.domain.usecase.SearchAnimeUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class AnimeActivityPresenterTest {

    private val view = mockk<AnimeActivityContract.View>()
    private val fetchAnimeListUseCase = mockk<FetchAnimeListUseCase>()
    private val saveAnimeUseCase = mockk<SaveAnimeUseCase>()
    private val searchAnimeUseCase = mockk<SearchAnimeUseCase>()
    private val testScheduler: TestScheduler = TestScheduler()

    private val presenter = AnimeActivityPresenter(
        view,
        fetchAnimeListUseCase,
        saveAnimeUseCase,
        searchAnimeUseCase
    )

    @BeforeEach
    fun setup() {
        every { fetchAnimeListUseCase() } returns mockk()
        every { saveAnimeUseCase(any()) } returns mockk()
        every { searchAnimeUseCase(any()) } returns mockk()

        every { view.getAnimeSaveButtonClicks() } returns Observable.never()
        every { view.getAnimeSearchInputs() } returns Observable.never()
    }

    @Test
    fun `WHEN start is called THEN observe anime save button clicks`() {
        val publisher = PublishSubject.create<Int>()
        val animeId = 101

        every { view.getAnimeSaveButtonClicks() } returns publisher

        presenter.start()
        testScheduler.triggerActions()

        publisher.onNext(animeId)
        testScheduler.advanceTimeTo(201, TimeUnit.MILLISECONDS)

        verify { saveAnimeUseCase(animeId) }
    }

    @Test
    fun `WHEN start is called THEN observe anime search inputs`() {
        val publisher = PublishSubject.create<String>()
        val animeName = "Naruto"

        every { view.getAnimeSearchInputs() } returns publisher

        presenter.start()
        testScheduler.triggerActions()

        publisher.onNext(animeName)
        testScheduler.advanceTimeTo(201, TimeUnit.MILLISECONDS)

        verify { searchAnimeUseCase(animeName) }
    }

    @Test
    fun `WHEN start is called THEN fetch anime list`() {
        presenter.start()

        verify { fetchAnimeListUseCase() }
    }
}