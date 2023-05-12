package com.asgh.themoviedb.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.commons.utils.TestUtils
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.use_case.TMDBGenreUseCase
import com.asgh.themoviedb.domain.use_case.TMDBMovieUseCase
import com.asgh.themoviedb.presentation.modules.detail.TMDBDetailVM
import com.example.local.entity.TMDBGenreEntity
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class TMDBDetailVMTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val useCase = mockk<TMDBGenreUseCase>(relaxed = true)
    private val movieUseCase = mockk<TMDBMovieUseCase>(relaxed = true)
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
    private val sut = TMDBDetailVM(dispatcher, useCase, movieUseCase, savedStateHandle)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        savedStateHandle["movie"] = 0
    }

    @Test
    fun `obtener el detalle de una pelicula por medio de su ID, responde loading`() {
        //given
        val movieId = 0
        coEvery { movieUseCase.invoke(movieId) } returns flow { emit(TMDBEither.Loading) }

        //when
        sut.getMovieById(movieId)

        //then
        val isLoading = sut.isLoading.value
        isLoading.let {
            Truth.assertThat(it).isEqualTo(true)
        }
    }

    @Test
    fun `obtener el detalle de una pelicula por medio de su ID, responde success`() = runTest {
        //given
        val movieId = 0
        val movie = TMDBMovie(id = movieId)
        coEvery { movieUseCase.invoke(movieId) } returns flow { emit(TMDBEither.Success(movie)) }

        //when
        sut.getMovieById(movieId)
        advanceUntilIdle()

        //then
        val selectedMovie = sut.selectedMovie.value
        val isLoading = sut.isLoading.value
        isLoading.let {
            Truth.assertThat(it).isEqualTo(false)
        }
        selectedMovie.let {
            Truth.assertThat(it).isInstanceOf(TMDBMovie::class.java)
            Truth.assertThat(it).isEqualTo(movie)
        }
    }

    @Test
    fun `obtener los generos de la pelicula obtenida, responde success`() = runTest {
        //given
        val movie = TMDBMovie(id = 1)
        val movieWithGenres = TMDBMovieWithGenres(
            TestUtils.movieEntityList[0],
            listOf(TMDBGenreEntity(0, "Action"))
        )
        coEvery { useCase.getMovieWithGenres(movie.id) } returns flow {
            emit(TMDBEither.Success(movieWithGenres))
        }

        //when
        sut.setSelectedMovie(movie)

        //then
        val movieGenres = sut.movieGenres.value
        movieGenres.let {
            Truth.assertThat(it).isInstanceOf(String::class.java)
            Truth.assertThat(it).isEqualTo("Action")
        }
    }

    @Test
    fun `obtener los generos de la pelicula obtenida, responde failure`() = runTest {
        //given
        val movie = TMDBMovie(id = 1)
        coEvery { useCase.getMovieWithGenres(movie.id) } returns flow {
            emit(TMDBEither.Failure(""))
        }

        //when
        sut.setSelectedMovie(movie)

        //then
        val movieGenres = sut.movieGenres.value
        movieGenres.let {
            Truth.assertThat(it).isInstanceOf(String::class.java)
            Truth.assertThat(it).isEqualTo("")
        }
    }

    @Test
    fun `obtener las peliculas relacionadas por genero de la pelicula obtenida, responde success`() = runTest {
        //given
        val movie = TMDBMovie(id = 1)
        val movieWithGenres = TMDBMovieWithGenres(
            TestUtils.movieEntityList[0],
            listOf(TMDBGenreEntity(0, "Action"))
        )
        val moviesInGenre = listOf(TMDBMoviesInGenre(
            TMDBGenreEntity(0, "Action"),
            TestUtils.movieEntityList
        ))
        coEvery { useCase.getMovieWithGenres(movie.id) } returns flow {
            emit(TMDBEither.Success(movieWithGenres))
        }
        coEvery { useCase.getMoviesInGenre(0) } returns flow {
            emit(TMDBEither.Success(moviesInGenre))
        }

        //when
        sut.setSelectedMovie(movie)

        //then
        val relatedMovies = sut.relatedMovies
        relatedMovies.let {
            Truth.assertThat(it).isEqualTo(moviesInGenre)
        }
    }
}