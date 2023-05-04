package com.asgh.themoviedb.data.repository

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.commons.internet.ConnectionVerifier
import com.asgh.themoviedb.commons.utils.TestUtils
import com.asgh.themoviedb.data.mapper.toLatestMovie
import com.asgh.themoviedb.data.mapper.toMovie
import com.example.local.dao.TMDBCrossRefDao
import com.example.local.dao.TMDBGenreDao
import com.example.local.dao.TMDBLatestMovieDao
import com.example.local.dao.TMDBMoviesDao
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import com.asgh.themoviedb.data.remote.api.TMDBApiInfoType
import com.asgh.themoviedb.data.remote.api.TMDBEndPoint
import com.asgh.themoviedb.data.remote.api.TMDBMovieApi
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBRepositoryImpTest {

    val api = mockk<TMDBMovieApi>(relaxed = true)
    val moviesDao = mockk<TMDBMoviesDao>(relaxed = true)
    val genreDao = mockk<TMDBGenreDao>(relaxed = true)
    val latestMovieDao = mockk<TMDBLatestMovieDao>(relaxed = true)
    val crossRefDao = mockk<TMDBCrossRefDao>(relaxed = true)
    val internetVerifier = mockk<ConnectionVerifier>(relaxed = true)
    lateinit var sut: TMDBRepositoryImp
    
    @Before
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        val scheduler = Schedulers.trampoline()
        sut = TMDBRepositoryImp(dispatcher, scheduler, api, moviesDao, genreDao, latestMovieDao, crossRefDao, internetVerifier)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `obtener las peliculas de la categoria now playing cuando hay internet regresar datos del api`()
    = runTest {
        //given
        val list = TestUtils.movieResponseDtoList
        val expectedList = TMDBMovieSeriesResponse(results = list).toMovies()
        every { internetVerifier.hasInternetConnection() } returns (true)
        coEvery { api.getMoviesOrSeries(
            type = TMDBApiInfoType.MOVIES.type,
            endPoint = TMDBEndPoint.NOW_PLAYING.endPoint)
        } returns Response.success(TMDBMovieSeriesResponse(results = list))

        //when
        val result = sut.getNowPlayingMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedList)
        )
    }

    @Test
    fun `obtener las peliculas de la categoria now playing cuando no hay internet y la base de datos esta vacia regresa error`()
    = runTest {
        //given
        val list = listOf<TMDBMovieEntity>()
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { moviesDao.getMovies(TMDBEndPoint.NOW_PLAYING.endPoint) } returns list

        //when
        val result = sut.getNowPlayingMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener las peliculas de la categoria now playing cuando no hay internet y la base de datos no esta vacia regresa la informacion de la base de datos`()
    = runTest {
        //given
        val list = TestUtils.movieEntityList
        val expectedList = list.map { it.toMovie() }
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { moviesDao.getMovies(TMDBEndPoint.NOW_PLAYING.endPoint) } returns list

        //when
        val result = sut.getNowPlayingMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedList)
        )
    }

    @Test
    fun `obtener las peliculas de la categoria top rated cuando hay internet regresar datos del api`()
    = runTest {
        //given
        val list = TestUtils.movieResponseDtoList
        val expectedList = TMDBMovieSeriesResponse(results = list).toMovies()
        every { internetVerifier.hasInternetConnection() } returns (true)
        coEvery { api.getMoviesOrSeries(
            type = TMDBApiInfoType.MOVIES.type,
            endPoint = TMDBEndPoint.TOP_RATED.endPoint)
        } returns Response.success(TMDBMovieSeriesResponse(results = list))

        //when
        val result = sut.getTopRatedMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedList)
        )
    }

    @Test
    fun `obtener las peliculas de la categoria top rated cuando no hay internet y la base de datos esta vacia regresa error`()
    = runTest {
        //given
        val list = listOf<TMDBMovieEntity>()
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { moviesDao.getMovies(TMDBEndPoint.TOP_RATED.endPoint) } returns list

        //when
        val result = sut.getTopRatedMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener las peliculas de la categoria top rated cuando no hay internet y la base de datos no esta vacia regresa la informacion de la base de datos`()
            = runTest {
        //given
        val list = TestUtils.movieEntityList
        val expectedList = list.map { it.toMovie() }
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { moviesDao.getMovies(TMDBEndPoint.TOP_RATED.endPoint) } returns list

        //when
        val result = sut.getTopRatedMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedList)
        )
    }

    @Test
    fun `obtener la pelicula mas reciente cuando hay internet regresar datos del api`()
    = runTest {
        //given
        val movie = TestUtils.latestMovieResponse
        val expectedMovie = movie.toLatestMovie()
        every { internetVerifier.hasInternetConnection() } returns (true)
        coEvery { api.getLatestMoviesOrSeries(
            type = TMDBApiInfoType.MOVIES.type)
        } returns Response.success(movie)

        //when
        val result = sut.getLatestMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedMovie)
        )
    }

    @Test
    fun `obtener la pelicula mas reciente cuando no hay internet y la base de datos esta vacia regresa error`()
    = runTest {
        //given
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { latestMovieDao.getLatestMovie() } returns null

        //when
        val result = sut.getLatestMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener la pelicula mas reciente cuando no hay internet y la base de datos no esta vacia regresa la informacion de la base de datos`()
    = runTest {
        //given
        val movie = TestUtils.latestMovieResponse.toLatestMovieEntity()
        val expectedMovie = movie.toLatestMovie()
        every { internetVerifier.hasInternetConnection() } returns (false)
        coEvery { latestMovieDao.getLatestMovie() } returns movie

        //when
        val result = sut.getLatestMovies()

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedMovie)
        )
    }

    @Test
    fun `obtener la referencia cruzada de una pelicula con todos sus generos cuando la base de datos esta vacia regresa error`()
    = runTest {
        //given
        val movieId = 0
        every { moviesDao.getMovieWithGenres(movieId) } returns null

        //when
        val result = sut.getMovieWithGenres(movieId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener la referencia cruzada de una pelicula con todos sus generos cuando la base de datos no esta vacia regresa la referencia cruzada`()
    = runTest {
        //given
        val movie = TestUtils.movieEntityList[0]
        val genres = listOf(TMDBGenreEntity(0, "Science Fiction"))
        val movieWithGenres = TMDBMovieWithGenres(movie, genres)
        every { moviesDao.getMovieWithGenres(movie.movieId) } returns movieWithGenres

        //when
        val result = sut.getMovieWithGenres(movie.movieId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(movieWithGenres)
        )
    }

    @Test
    fun `obtener la referencia cruzada de un genero con todas sus peliculas cuando la base de datos esta vacia regresa error`()
    = runTest {
        //given
        val genreId = 0
        every { genreDao.getMoviesInGenre(genreId) } returns null

        //when
        val result = sut.getMoviesInGenre(genreId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener la referencia cruzada de un genero con todas sus peliculas cuando la base de datos no esta vacia regresa la referencia cruzada`()
    = runTest {
        //given
        val genre = TMDBGenreEntity(0, "Science Fiction")
        val movies = TestUtils.movieEntityList
        val moviesInGenre = listOf(TMDBMoviesInGenre(genre, movies))
        every { genreDao.getMoviesInGenre(genre.genreId) } returns moviesInGenre

        //when
        val result = sut.getMoviesInGenre(genre.genreId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(moviesInGenre)
        )
    }

    @Test
    fun `obtener una pelicula por su id cuando la base de datos esta vacia regresa error`()
    = runTest {
        //given
        val movieId = 0
        every { moviesDao.getMovie(movieId) } returns null

        //when
        val result = sut.getMovieById(movieId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Failure("")
        )
    }

    @Test
    fun `obtener una pelicula por su id cuando la base de datos no esta vacia regresa la informacion de la base de datos`()
    = runTest {
        //given

        val movie = TestUtils.movieEntityList[0]
        val expectedMovie = movie.toMovie()
        every { moviesDao.getMovie(movie.movieId) } returns movie

        //when
        val result = sut.getMovieById(movie.movieId)

        //then
        Truth.assertThat(result.first()).isEqualTo(
            TMDBEither.Success(expectedMovie)
        )
    }
}