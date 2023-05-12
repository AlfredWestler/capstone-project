package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.R
import com.asgh.themoviedb.TMDBApplication
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBMovieUseCaseTest {

    private val repositoryImp = mockk<TMDBRepositoryImp>(relaxed = true)
    private lateinit var sut: TMDBMovieUseCase

    @Before
    fun setUp() {
        sut = TMDBMovieUseCase(repositoryImp)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener el detalle de una pelicula por medio de su id, responde success`()
    = runTest {
        //given
        val movie = TMDBMovie()
        coEvery { repositoryImp.getMovieById(movie.id) } returns flow {
            emit(TMDBEither.Success(movie))
        }
        //when
        val result = sut(movie.id)
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Success(movie))
    }

    @Test
    fun `obtener el detalle de una pelicula por medio de su id, responde failure`()
    = runTest {
        //given
        val movie = TMDBMovie()
        coEvery { repositoryImp.getMovieById(movie.id) } returns flow {
            emit(TMDBEither.Failure(""))
        }
        //when
        val result = sut(movie.id)
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
    }
}