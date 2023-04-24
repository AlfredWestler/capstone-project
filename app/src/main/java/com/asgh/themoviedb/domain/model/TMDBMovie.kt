package com.asgh.themoviedb.domain.model

import com.asgh.themoviedb.data.local.entity.TMDBMovieEntity

data class TMDBMovie(
    val adult : Boolean = false,
    val backdropPath : String = "",
    val id : Int = 0,
    val originalLanguage : String = "",
    val originalTitle : String = "",
    val overview : String = "",
    val popularity : Double = 0.0,
    val posterPath : String = "",
    val releaseDate : String = "",
    val title : String = "",
    val video : Boolean = false,
    val voteAverage : Double = 0.0,
    val voteCount : Int = 0,
) {
    fun toEntity(type: String): TMDBMovieEntity = TMDBMovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        movieId = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        type = type
    )
}