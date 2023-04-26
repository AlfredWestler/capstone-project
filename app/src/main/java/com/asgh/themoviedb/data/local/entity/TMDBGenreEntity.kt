package com.asgh.themoviedb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asgh.themoviedb.domain.model.TMDBGenre

@Entity(tableName = "genre_table")
data class TMDBGenreEntity(
    @PrimaryKey val genreId: Int,
    val genreName: String?
) {
    fun toGenre(): TMDBGenre = TMDBGenre(
        id = genreId,
        name = genreName.orEmpty()
    )
}

