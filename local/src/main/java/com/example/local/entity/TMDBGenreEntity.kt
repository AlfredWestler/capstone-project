package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class TMDBGenreEntity(
    @PrimaryKey val genreId: Int,
    val genreName: String?
)

