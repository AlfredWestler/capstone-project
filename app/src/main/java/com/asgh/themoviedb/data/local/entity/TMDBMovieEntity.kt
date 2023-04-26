package com.asgh.themoviedb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asgh.themoviedb.domain.model.TMDBMovie

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