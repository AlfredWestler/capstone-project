package com.asgh.themoviedb.data.mapper

import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.domain.model.TMDBGenre
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBLatestMovieEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.relation.TMDBMovieWithGenres

fun TMDBLatestMovieEntity.toLatestMovie() = TMDBLatestMovie(
    adult = adult ?: false,
    budget = budget ?: 0,
    genres = emptyList(),
    homepage = homepage.orEmpty(),
    id = movieId,
    imdb_id = imdb_id.orEmpty(),
    original_language = original_language.orEmpty(),
    original_title = original_title.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0,
    poster_path = "${BuildConfig.IMAGE_URL}$poster_path",
    release_date = release_date.orEmpty(),
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    status = status.orEmpty(),
    tagline = tagline.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    vote_average = vote_average ?: 0,
    vote_count = vote_count ?: 0,
    status_message = status_message.orEmpty(),
    status_code = status_code ?: 0
)

fun TMDBGenreEntity.toGenre() = TMDBGenre(
    id = genreId,
    name = genreName.orEmpty()
)

fun TMDBMovieEntity.toMovie(): TMDBMovie = TMDBMovie(
    adult = this.adult ?: false,
    backdropPath = this.backdropPath ?: "",
    id = this.movieId,
    originalLanguage = this.originalLanguage ?: "",
    originalTitle = this.originalTitle ?: "",
    overview = this.overview ?: "",
    popularity = this.popularity ?: 0.0,
    posterPath = this.posterPath ?: "",
    releaseDate = this.releaseDate ?: "",
    title = this.title ?: "",
    video = this.video ?: false,
    voteAverage = this.voteAverage ?: 0.0,
    voteCount = this.voteCount ?: 0
)