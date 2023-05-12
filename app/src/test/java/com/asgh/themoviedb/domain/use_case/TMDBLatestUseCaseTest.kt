package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.commons.utils.TestUtils
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.example.local.entity.TMDBGenreEntity
import com.example.local.relation.TMDBMovieWithGenres
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
class TMDBLatestUseCaseTest {

    private val repositoryImp = mockk<TMDBRepositoryImp>(relaxed = true)
    private lateinit var sut: TMDBLatestUseCase

    @Before
    fun setUp() {
        sut = TMDBLatestUseCase(repositoryImp)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener la ultima pelicula registrada, responde un success`() =
        runTest {
            //given
            val latestMovie = TMDBLatestMovie()
            coEvery { repositoryImp.getLatestMovies() } returns flow {
                emit(TMDBEither.Success(latestMovie))
            }
            //when
            val result = sut.getLatestAsFlow()

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(TMDBEither.Success(latestMovie))
        }

    @Test
    fun `obtener la ultima pelicula registrada, responde un failure`() =
        runTest {
            //given
            coEvery { repositoryImp.getLatestMovies() } returns flow {
                emit(TMDBEither.Failure(""))
            }
            //when
            val result = sut.getLatestAsFlow()

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
        }
}