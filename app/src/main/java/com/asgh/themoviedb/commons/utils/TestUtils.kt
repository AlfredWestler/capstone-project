package com.asgh.themoviedb.commons.utils

import com.example.remote.dto.TMDBMovieSeriesResultDto
import com.example.remote.response.TMDBLatestMovieResponse
import com.example.local.entity.TMDBMovieEntity

object TestUtils {
    val movieResponseDtoList = listOf(TMDBMovieSeriesResultDto(id = 1))

    val movieEntityList = listOf(
        TMDBMovieEntity(
            movieId = 1,
            adult = null,
            backdropPath = null,
            originalLanguage = null,
            originalTitle = null,
            overview = null,
            popularity = null,
            posterPath = null,
            releaseDate = null,
            title = null,
            video = null,
            voteAverage = null,
            voteCount = null,
            type = null
        )
    )

    val latestMovieResponse = TMDBLatestMovieResponse(id = 1)
}