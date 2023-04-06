package com.asgh.themoviedb.data.remote.dto

import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.model.TMDBTvShow
import com.squareup.moshi.Json

data class TMDBMovieSeriesResultDto(
    @Json(name = "adult") val adult : Boolean? = null,
    @Json(name = "backdrop_path") val backdrop_path : String? = null,
    @Json(name = "id") val id : Int? = null,
    @Json(name = "original_language") val original_language : String? = null,
    @Json(name = "original_title") val original_title : String? = null, //solo para movies
    @Json(name = "overview") val overview : String? = null,
    @Json(name = "popularity") val popularity : Double? = null,
    @Json(name = "poster_path") val poster_path : String? = null,
    @Json(name = "release_date") val release_date : String? = null,
    @Json(name = "title") val title : String? = null, //solo para movies
    @Json(name = "video") val video : Boolean? = null,
    @Json(name = "vote_average") val vote_average : Double? = null,
    @Json(name = "vote_count") val vote_count : Int? = null,
    @Json(name = "first_air_date") val first_air_date : String? = null, //solo para tv
    @Json(name = "name") val name : String? = null, //solo para tv
    @Json(name = "original_name") val original_name : String? = null //solo para tv
) {
    fun toMovie(): TMDBMovie = TMDBMovie(
        adult = adult ?: false,
        backdropPath = backdrop_path.orEmpty(),
        id = id ?: 0,
        originalLanguage = original_language.orEmpty(),
        originalTitle = original_title.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        title = title.orEmpty(),
        video = video ?: false,
        voteAverage = vote_average ?: 0.0,
        voteCount = vote_count ?: 0
    )

    fun toTvShow(): TMDBTvShow = TMDBTvShow(
        adult = adult ?: false,
        backdropPath = backdrop_path.orEmpty(),
        id = id ?: 0,
        originalLanguage = original_language.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        video = video ?: false,
        voteAverage = vote_average ?: 0.0,
        voteCount = vote_count ?: 0,
        firstAirDate = first_air_date.orEmpty(),
        name = name.orEmpty(),
        originalName = original_name.orEmpty()
    )
}