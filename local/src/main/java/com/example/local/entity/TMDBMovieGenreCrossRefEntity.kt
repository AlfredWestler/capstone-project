package com.example.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movies_genre_cross_ref", primaryKeys = ["genreId", "movieId"],
    foreignKeys = [
        ForeignKey(entity = TMDBMovieEntity::class, parentColumns = ["movieId"], childColumns = ["movieId"]),
        ForeignKey(entity = TMDBGenreEntity::class, parentColumns = ["genreId"], childColumns = ["genreId"])
    ]
)
data class TMDBMovieGenreCrossRefEntity(
    val genreId: Int,
    val movieId: Int
)
