package com.example.remote.dto

import com.squareup.moshi.Json

data class TMDBDatesDto(
    @Json(name = "maximum") val maximum : String? = null,
    @Json(name = "minimum") val minimum : String? = null
)
