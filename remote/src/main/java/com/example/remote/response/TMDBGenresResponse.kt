package com.example.remote.response

import com.example.remote.dto.TMDBGenreDto
import com.squareup.moshi.Json

data class TMDBGenresResponse(
    @Json(name = "genres") val genres: List<TMDBGenreDto>? = null
)
