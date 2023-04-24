package com.asgh.themoviedb.domain.model

import java.util.Date

data class TMDBDates(
    val maximum : Date = Date(),
    val minimum : Date = Date()
)