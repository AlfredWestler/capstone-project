package com.example.remote.dto

import com.squareup.moshi.Json

data class TMDBGenreDto(
    @Json(name = "id") val id: Int? = null,
    @Json (name = "name") val name: String? = null
)