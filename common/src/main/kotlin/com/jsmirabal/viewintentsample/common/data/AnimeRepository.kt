package com.jsmirabal.viewintentsample.common.data

import com.jsmirabal.viewintentsample.common.domain.core.Either
import com.jsmirabal.viewintentsample.common.domain.model.AnimeError
import com.jsmirabal.viewintentsample.common.domain.model.AnimeResult

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
