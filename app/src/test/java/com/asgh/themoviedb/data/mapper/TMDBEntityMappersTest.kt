package com.asgh.themoviedb.data.mapper

import com.asgh.themoviedb.commons.utils.TestUtils
import com.asgh.themoviedb.domain.model.TMDBGenre
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBLatestMovieEntity
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test

class TMDBEntityMappersTest {

    @Before
    fun setUp() {}

    @After
    fun tearDown() {}

    @Test
    fun `transforma el objeto entity LatestMovie a un objeto final LatestMovie`() {
        //given
        val latestMovieEntity = TMDBLatestMovieEntity(
            0,
            0,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val latestMovie = TMDBLatestMovie(poster_path = "https://image.tmdb.org/t/p/originalnull")

        //when
        val result = latestMovieEntity.toLatestMovie()

        //then
        Truth.assertThat(result).isEqualTo(latestMovie)
    }

    @Test
    fun `transforma el objeto entity Genre a un objeto final Genre`() {
        //given
        val genreEntity = TMDBGenreEntity(0, null)
        val genre = TMDBGenre()

        //when
        val result = genreEntity.toGenre()

        //then
        Truth.assertThat(result).isEqualTo(genre)
    }

    @Test
    fun `transforma el objeto entity Movie a un objeto final Movie`() {
        //given
        val movieEntity = TestUtils.movieEntityList[0]
        val movie = TMDBMovie(id = 1)

        //when
        val result = movieEntity.toMovie()

        //then
        Truth.assertThat(result).isEqualTo(movie)
    }
}