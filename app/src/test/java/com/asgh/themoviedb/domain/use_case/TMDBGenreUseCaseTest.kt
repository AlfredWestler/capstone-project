package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.commons.utils.TestUtils
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.example.local.entity.TMDBGenreEntity
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBGenreUseCaseTest {

    private val repositoryImp = mockk<TMDBRepositoryImp>(relaxed = true)
    private lateinit var sut: TMDBGenreUseCase

    @Before
    fun setUp() {
        sut = TMDBGenreUseCase(repositoryImp)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener una referencia cruzada de una pelicula con sus generos responde un success`() =
        runTest {
            //given
            val movieId = 0
            val movieEntity = TestUtils.movieEntityList[0]
            val genreEntityList = listOf(TMDBGenreEntity(0, ""))
            coEvery { repositoryImp.getMovieWithGenres(movieId) } returns flow {
                emit(TMDBEither.Success(TMDBMovieWithGenres(movieEntity, genreEntityList)))
            }
            //when
            val result = sut.getMovieWithGenres(movieId)

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(
                TMDBEither.Success(TMDBMovieWithGenres(movieEntity, genreEntityList))
            )
        }

    @Test
    fun `obtener una referencia cruzada de una pelicula con sus generos responde un failure`() =
        runTest {
            //given
            val movieId = 0
            coEvery { repositoryImp.getMovieWithGenres(movieId) } returns flow {
                emit(TMDBEither.Failure(""))
            }
            //when
            val result = sut.getMovieWithGenres(movieId)

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
        }

    @Test
    fun `obtener una referencia cruzada de un genero con sus peliculas, responde un success`() =
        runTest {
            //given
            val genreId = 0
            val genreEntity = TMDBGenreEntity(0, "")
            val movieEntityList = TestUtils.movieEntityList
            coEvery { repositoryImp.getMoviesInGenre(genreId) } returns flow {
                emit(TMDBEither.Success(listOf(TMDBMoviesInGenre(genreEntity, movieEntityList))))
            }
            //when
            val result = sut.getMoviesInGenre(genreId)

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(
                TMDBEither.Success(listOf(TMDBMoviesInGenre(genreEntity, movieEntityList)))
            )
        }

    @Test
    fun `obtener una referencia cruzada de un genero con sus peliculas, responde un failure`() =
        runTest {
            //given
            val genreId = 0
            coEvery { repositoryImp.getMoviesInGenre(genreId) } returns flow {
                emit(TMDBEither.Failure(""))
            }
            //when
            val result = sut.getMoviesInGenre(genreId)

            //then
            Truth.assertThat(result.first()).isEqualTo(TMDBEither.Loading)
            Truth.assertThat(result.last()).isEqualTo(TMDBEither.Failure(""))
        }
}