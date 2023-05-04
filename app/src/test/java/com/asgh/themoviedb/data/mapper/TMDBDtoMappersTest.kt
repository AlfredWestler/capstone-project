package com.asgh.themoviedb.data.mapper

import com.asgh.themoviedb.domain.model.TMDBGenre
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.remote.api.TMDBEndPoint
import com.example.remote.dto.TMDBGenreDto
import com.example.remote.dto.TMDBMovieSeriesResultDto
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test

class TMDBDtoMappersTest {

    @Before
    fun setUp() {}

    @After
    fun tearDown() {}

    @Test
    fun `transforma el objeto dto Genre a un objeto final Genre`() {
        //given
        val genreDto = TMDBGenreDto()
        val genre = TMDBGenre()

        //when
        val result = genreDto.toGenre()

        //then
        Truth.assertThat(result).isEqualTo(genre)
    }

    @Test
    fun `transforma el objeto dto Genre a un objeto entity Genre`() {
        //given
        val genreDto = TMDBGenreDto()
        val genreEntity = TMDBGenreEntity(0, "")

        //when
        val result = genreDto.toGenreEntity()

        //then
        Truth.assertThat(result).isEqualTo(genreEntity)
    }

    @Test
    fun `transforma el objeto dto Movie a un objeto fianl Movie`() {
        //given
        val movieDto = TMDBMovieSeriesResultDto()
        val movie = TMDBMovie().copy(
            backdropPath = "https://image.tmdb.org/t/p/originalnull",
            posterPath = "https://image.tmdb.org/t/p/originalnull"
        )

        //when
        val result = movieDto.toMovie()

        //then
        Truth.assertThat(result).isEqualTo(movie)
    }

    @Test
    fun `transforma el objeto dto Movie a un objeto entity Movie del tipo now playing`() {
        //given
        val movieDto = TMDBMovieSeriesResultDto(id = 1)
        val movieEntity = TMDBMovieEntity(
            movieId = 1,
            adult = false,
            backdropPath = "https://image.tmdb.org/t/p/originalnull",
            posterPath = "https://image.tmdb.org/t/p/originalnull",
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            releaseDate = "",
            title = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0,
            type = TMDBEndPoint.NOW_PLAYING.endPoint
        )

        //when
        val result = movieDto.transformToEntity(TMDBEndPoint.NOW_PLAYING.endPoint)

        //then
        Truth.assertThat(result).isEqualTo(movieEntity)
    }

    @Test
    fun `transforma el objeto dto Movie a un objeto entity Movie del tipo top rated`() {
        //given
        val movieDto = TMDBMovieSeriesResultDto(id = 1)
        val movieEntity = TMDBMovieEntity(
            movieId = 1,
            adult = false,
            backdropPath = "https://image.tmdb.org/t/p/originalnull",
            posterPath = "https://image.tmdb.org/t/p/originalnull",
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            releaseDate = "",
            title = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0,
            type = TMDBEndPoint.TOP_RATED.endPoint
        )

        //when
        val result = movieDto.transformToEntity(TMDBEndPoint.TOP_RATED.endPoint)

        //then
        Truth.assertThat(result).isEqualTo(movieEntity)
    }
}