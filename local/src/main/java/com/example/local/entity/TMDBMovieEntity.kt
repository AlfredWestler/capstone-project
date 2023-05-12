package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie_table")
data class TMDBMovieEntity(
    @PrimaryKey val movieId : Int,
    val adult : Boolean?,
    val backdropPath : String?,
    val originalLanguage : String?,
    val originalTitle : String?,
    val overview : String?,
    val popularity : Double?,
    val posterPath : String?,
    val releaseDate : String?,
    val title : String?,
    val video : Boolean?,
    val voteAverage : Double?,
    val voteCount : Int?,
    val type: String?
)