package com.asgh.themoviedb.commons.either

sealed class TMDBEither<out S, out F> {

    object Loading : TMDBEither<Nothing, Nothing>()
    data class Success<out S>(val success: S) : TMDBEither<S, Nothing>()
    data class Failure<out F>(val failure: F) : TMDBEither<Nothing, F>()

    suspend fun <S, F> TMDBEither<S, F>.onLoading(fn: suspend () -> Unit) : TMDBEither<S, F> =
        when (this){
            is Loading -> fn()
            else -> { /* PASS */ }
        }.let { this }

    suspend fun <S, F> TMDBEither<S, F>.onSuccess(fn: suspend (S) -> Unit) : TMDBEither<S, F> =
        when (this){
            is Success -> fn(success)
            else -> { /* PASS */ }
        }.let { this }

    suspend fun <S, F> TMDBEither<S, F>.onFailure(fn: suspend (F) -> Unit) : TMDBEither<S, F> =
        when (this){
            is Failure -> fn(failure)
            else -> { /* PASS */ }
        }.let { this }
}