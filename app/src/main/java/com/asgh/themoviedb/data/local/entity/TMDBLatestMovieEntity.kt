package com.asgh.themoviedb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.domain.model.TMDBLatestMovie

@Entity(tableName = "latest_movie_table")
data class TMDBLatestMovieEntity(
    @PrimaryKey val id: Int,
    val movieId: Int,
    val adult: Boolean?,
    val budget: Int?,
    val homepage: String?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Int?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Int?,
    val vote_count: Int?,
    val status_message: String?,
    val status_code: Int?
)

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
