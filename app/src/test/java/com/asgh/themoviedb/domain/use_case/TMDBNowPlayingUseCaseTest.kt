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
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBNowPlayingUseCaseTest {

    private val repositoryImp = mockk<TMDBRepositoryImp>(relaxed = true)
    private lateinit var sut: TMDBNowPlayingUseCase

    @Before
    fun setUp() {
        sut = TMDBNowPlayingUseCase(repositoryImp)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener un listado de peliculas de la categoria now playing, responde success`()
    = runTest {
        //given
        val movieList = listOf(TMDBMovie())
        coEvery { repositoryImp.getNowPlayingMovies() } returns flow {
            emit(TMDBEither.Success(movieList))
        }
        //when
        val result = sut.getNowPlayingMoviesModified()
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Success(movieList))
    }

    @Test
    fun `obtener un listado de peliculas de la categoria now playing, responde failure`()
    = runTest {
        //given
        coEvery { repositoryImp.getNowPlayingMovies() } returns flow {
            emit(TMDBEither.Failure(""))
        }
        //when
        val result = sut.getNowPlayingMoviesModified()
        //then
        Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
        Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
    }
}