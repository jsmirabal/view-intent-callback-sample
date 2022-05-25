package com.jsmirabal.viewintentsample.common.domain.usecase

import com.jsmirabal.viewintentsample.common.data.AnimeRepository

class FetchAnimeListUseCase(private val animeRepository: AnimeRepository) {

    operator fun invoke() = animeRepository.fetchAnimes()
}