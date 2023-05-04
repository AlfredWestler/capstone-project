package com.asgh.themoviedb.data.mapper

import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.domain.model.TMDBDates
import com.asgh.themoviedb.domain.model.TMDBGenre
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.model.TMDBTvShow
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.remote.dto.TMDBDatesDto
import com.example.remote.dto.TMDBGenreDto
import com.example.remote.dto.TMDBMovieSeriesResultDto
import java.time.Instant
import java.util.*

fun TMDBDatesDto.toDates(): TMDBDates = TMDBDates(
    maximum = Date.from(Instant.parse(maximum)),
    minimum = Date.from(Instant.parse(minimum))
)

fun TMDBGenreDto.toGenre() = TMDBGenre(
    id = id ?: 0,
    name = name ?: ""
)

fun TMDBGenreDto.toGenreEntity() = TMDBGenreEntity(
    genreId = id ?: 0,
    genreName = name ?: ""
)

fun TMDBMovieSeriesResultDto.toMovie(): TMDBMovie = TMDBMovie(
    adult = adult ?: false,
    backdropPath = "${BuildConfig.IMAGE_URL}$backdrop_path",
    id = id ?: 0,
    originalLanguage = original_language.orEmpty(),
    originalTitle = original_title.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0.0,
    posterPath = "${BuildConfig.IMAGE_URL}$poster_path",
    releaseDate = release_date.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    voteAverage = vote_average ?: 0.0,
    voteCount = vote_count ?: 0
)

fun TMDBMovieSeriesResultDto.toTvShow(): TMDBTvShow = TMDBTvShow(
    adult = adult ?: false,
    backdropPath = "${BuildConfig.IMAGE_URL}$backdrop_path",
    id = id ?: 0,
    originalLanguage = original_language.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0.0,
    posterPath = "${BuildConfig.IMAGE_URL}$poster_path",
    releaseDate = release_date.orEmpty(),
    video = video ?: false,
    voteAverage = vote_average ?: 0.0,
    voteCount = vote_count ?: 0,
    firstAirDate = first_air_date.orEmpty(),
    name = name.orEmpty(),
    originalName = original_name.orEmpty()
)

fun TMDBMovieSeriesResultDto.transformToEntity(type: String): TMDBMovieEntity = TMDBMovieEntity(
    adult = adult ?: false,
    backdropPath = "${BuildConfig.IMAGE_URL}$backdrop_path",
    movieId = id ?: 0,
    originalLanguage = original_language.orEmpty(),
    originalTitle = original_title.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0.0,
    posterPath = "${BuildConfig.IMAGE_URL}$poster_path",
    releaseDate = release_date.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    voteAverage = vote_average ?: 0.0,
    voteCount = vote_count ?: 0,
    type = type
)