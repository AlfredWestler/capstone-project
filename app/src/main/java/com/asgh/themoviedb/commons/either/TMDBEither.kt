package com.asgh.themoviedb.commons.either

sealed class TMDBEither<out S, out F> {

    object Loading : TMDBEither<Nothing, Nothing>()
    data class Success<out S>(val success: S) : TMDBEither<S, Nothing>()
    data class Failure<out F>(val failure: F) : TMDBEither<Nothing, F>()

    val isSuccess get() = this is Success<S>
    inline fun onLoading(fn: () -> Unit): TMDBEither<S, F> =
        when (this){
            is Loading -> fn()
            else -> { /* PASS */ }
        }.let { this }

    inline fun onSuccess(fn: (S) -> Unit) : TMDBEither<S, F> =
        when(this) {
            is Success -> fn(success)
            else -> {/*PASS*/}
        }.let { this }


    inline fun onFailure(fn: (F) -> Unit) : TMDBEither<S, F> =
        when (this){
            is Failure -> fn(failure)
            else -> { /* PASS */ }
        }.let { this }
}

fun <S,F> TMDBEither<S,F>?.isSuccess(): Boolean {
    return this?.isSuccess ?: run { false }
}