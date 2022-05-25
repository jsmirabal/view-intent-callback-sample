package com.jsmirabal.viewintentsample.common.data

import com.jsmirabal.viewintentsample.common.domain.core.Either

class AnimeRepository {

    fun searchAnime(animeName: String): Either<AnimeResult, AnimeError> {
        Thread.sleep(3000) // long operation
        return Either.Success(AnimeResult("info"))
    }

    fun saveAnime(animeId: Int): Either<AnimeResult, AnimeError> {
        Thread.sleep(3000) // long operation
        return Either.Success(AnimeResult("saved"))
    }

    fun fetchAnimes(): Either<AnimeResult, AnimeError> {
        Thread.sleep(3000) // long operation
        return Either.Success(AnimeResult("list"))
    }
}

data class AnimeResult(val animeInfo: String)
data class AnimeError(val animeError: String)
