package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.domain.repository.TMDBRepository
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBGenreUseCase @Inject constructor(
    private val repository: TMDBRepository
)  {

    fun getMovieWithGenres(movieId: Int): Flow<TMDBEither<TMDBMovieWithGenres, String>> = flow {
        emit(TMDBEither.Loading)
        repository.getMovieWithGenres(movieId).collect{ emit(it) }
    }.catch { emit(TMDBEither.Failure("")) }

    fun getMoviesInGenre(genreId: Int): Flow<TMDBEither<List<TMDBMoviesInGenre>, String>> = flow {
        emit(TMDBEither.Loading)
        repository.getMoviesInGenre(genreId).collect{ emit(it) }
    }.catch { emit(TMDBEither.Failure("")) }
}