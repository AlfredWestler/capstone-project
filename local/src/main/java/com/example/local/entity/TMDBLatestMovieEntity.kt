package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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