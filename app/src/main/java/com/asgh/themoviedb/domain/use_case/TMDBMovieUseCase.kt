package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.R
import com.asgh.themoviedb.TMDBApplication
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.repository.TMDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBMovieUseCase @Inject constructor(
    private val repository: TMDBRepository
)  {

    operator fun invoke(id: Int): Flow<TMDBEither<TMDBMovie, String>> = flow {
        emit(TMDBEither.Loading)
        repository.getMovieById(id).collect { emit(it) }
    }.catch { emit(TMDBEither.Failure(TMDBApplication.appContext.getString(R.string.generic_error_message))) }
}