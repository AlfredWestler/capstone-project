package com.asgh.themoviedb.domain.model

data class TMDBTvShow(
    val adult : Boolean = false,
    val backdropPath : String = "",
    val id : Int = 0,
    val originalLanguage : String = "",
    val overview : String = "",
    val popularity : Double = 0.0,
    val posterPath : String = "",
    val releaseDate : String = "",
    val video : Boolean = false,
    val voteAverage : Double = 0.0,
    val voteCount : Int = 0,
    val firstAirDate : String = "",
    val name : String = "",
    val originalName : String = ""
)