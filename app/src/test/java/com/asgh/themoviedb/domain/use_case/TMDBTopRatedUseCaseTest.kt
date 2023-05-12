package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBTopRatedUseCaseTest {

    private val repositoryImp = mockk<TMDBRepositoryImp>(relaxed = true)
    private lateinit var sut: TMDBTopRatedUseCase

    @Before
    fun setUp() {
        sut = TMDBTopRatedUseCase(repositoryImp)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener un listado de peliculas de la categoria top rated, responde success`()
    = runTest {
        //given
        val movieList = listOf(TMDBMovie())
        coEvery { repositoryImp.getTopRatedMovies() } returns flow {
            emit(TMDBEither.Success(movieList))
        }
        //when
        val result = sut.getTopRatedMoviesAsFlow()
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Success(movieList))
    }

    @Test
    fun `obtener un listado de peliculas de la categoria top rated, responde failure`()
    = runTest {
        //given
        coEvery { repositoryImp.getTopRatedMovies() } returns flow {
            emit(TMDBEither.Failure(""))
        }
        //when
        val result = sut.getTopRatedMoviesAsFlow()
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
    }
}