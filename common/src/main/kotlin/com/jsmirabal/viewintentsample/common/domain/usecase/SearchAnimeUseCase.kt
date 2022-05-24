package com.jsmirabal.viewintentsample.common.domain.usecase

import com.jsmirabal.viewintentsample.common.data.AnimeRepository

class SearchAnimeUseCase(private val animeRepository: AnimeRepository) {

    operator fun invoke(animeName: String) = animeRepository.searchAnime(animeName)
}