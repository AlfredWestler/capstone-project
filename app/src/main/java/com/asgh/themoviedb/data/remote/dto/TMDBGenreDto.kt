package com.asgh.themoviedb.data.remote.dto

import com.example.local.entity.TMDBGenreEntity
import com.asgh.themoviedb.domain.model.TMDBGenre
import com.squareup.moshi.Json

data class TMDBGenreDto(
    @Json(name = "id") val id: Int? = null,
    @Json (name = "name") val name: String? = null
) {
    fun toGenre() = TMDBGenre(
        id = id ?: 0,
        name = name ?: ""
    )

    fun toGenreEntity() = TMDBGenreEntity(
        genreId = id ?: 0,
        genreName = name ?: ""
    )
}