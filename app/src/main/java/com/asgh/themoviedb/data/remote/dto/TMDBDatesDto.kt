package com.asgh.themoviedb.data.remote.dto

import com.asgh.themoviedb.domain.model.TMDBDates
import com.squareup.moshi.Json
import java.time.Instant
import java.util.Date

data class TMDBDatesDto(
    @Json(name = "maximum") val maximum : String? = null,
    @Json(name = "minimum") val minimum : String? = null
) {

    fun toDates(): TMDBDates = TMDBDates(
        maximum = Date.from(Instant.parse(maximum)),
        minimum = Date.from(Instant.parse(minimum))
    )
}
