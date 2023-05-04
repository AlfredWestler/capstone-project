package com.asgh.themoviedb.data.remote.response

import com.example.local.entity.TMDBGenreEntity
import com.asgh.themoviedb.data.remote.dto.TMDBGenreDto
import com.squareup.moshi.Json

data class TMDBGenresResponse(
    @Json(name = "genres") val genres: List<TMDBGenreDto>? = null
) {
    fun toGenresEntity(): List<TMDBGenreEntity> {
        return genres?.map { it.toGenreEntity() } ?: emptyList()
    }
}
