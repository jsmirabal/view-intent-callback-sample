package com.jsmirabal.viewintentsample.common.domain.usecase

import com.jsmirabal.viewintentsample.common.data.AnimeRepository

class SaveAnimeUseCase(private val animeRepository: AnimeRepository) {

    operator fun invoke(animeId: Int) = animeRepository.saveAnime(animeId)
}