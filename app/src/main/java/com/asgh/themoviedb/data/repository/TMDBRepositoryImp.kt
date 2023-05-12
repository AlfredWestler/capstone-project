package com.asgh.themoviedb.data.repository

import com.asgh.themoviedb.R
import com.asgh.themoviedb.TMDBApplication
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.commons.internet.ConnectionVerifier
import com.asgh.themoviedb.data.mapper.*
import com.example.local.dao.TMDBCrossRefDao
import com.example.local.dao.TMDBGenreDao
import com.example.local.dao.TMDBLatestMovieDao
import com.example.local.dao.TMDBMoviesDao
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import com.example.remote.api.TMDBApiInfoType
import com.example.remote.api.TMDBEndPoint
import com.example.remote.api.TMDBMovieApi
import com.example.remote.response.TMDBGenresResponse
import com.example.remote.response.TMDBLatestMovieResponse
import com.example.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.repository.TMDBRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class TMDBRepositoryImp @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val scheduler: Scheduler,
    private val api: TMDBMovieApi,
    private val moviesDao: TMDBMoviesDao,
    private val genreDao: TMDBGenreDao,
    private val latestMovieDao: TMDBLatestMovieDao,
    private val crossRefDao: TMDBCrossRefDao,
    private val internetVerifier: ConnectionVerifier
): TMDBRepository {

    private var genresGotten = false
    override fun getNowPlayingMovies(): Flow<TMDBEither<List<TMDBMovie>, String>> =
        flow {
            if (internetVerifier.hasInternetConnection()) {
                val response = api.getMoviesOrSeries(
                    type = TMDBApiInfoType.MOVIES.type,
                    endPoint = TMDBEndPoint.NOW_PLAYING.endPoint
                )
                response.onStatusListener(
                    success = {
                        saveMoviesInDB(it, TMDBEndPoint.NOW_PLAYING.endPoint)
                        emit(TMDBEither.Success(it?.toMovies() ?: emptyList()))
                    },
                    failure = { emit(TMDBEither.Failure(it)) }
                )
            } else {
                val movies = getMovies(TMDBEndPoint.NOW_PLAYING.endPoint)
                if(movies.isEmpty()){
                    emit(TMDBEither.Failure(""))
                } else {
                    emit(TMDBEither.Success(movies))
                }
            }
        }.flowOn(dispatcher)
    override fun getTopRatedMovies(): Flow<TMDBEither<List<TMDBMovie>, String>> =
        flow {
            if(internetVerifier.hasInternetConnection()) {
                val response = api.getMoviesOrSeries(
                    type = TMDBApiInfoType.MOVIES.type,
                    endPoint = TMDBEndPoint.TOP_RATED.endPoint
                )
                response.onStatusListener(
                    success = {
                        saveMoviesInDB(it, TMDBEndPoint.TOP_RATED.endPoint)
                        emit(TMDBEither.Success(it?.toMovies() ?: emptyList()))
                    },
                    failure = { emit(TMDBEither.Failure(it)) }
                )
            } else {
                val movies = getMovies(TMDBEndPoint.TOP_RATED.endPoint)
                if(movies.isEmpty()){
                    emit(TMDBEither.Failure(""))
                } else {
                    emit(TMDBEither.Success(movies))
                }
            }
        }.flowOn(dispatcher)

    override fun getLatestMovies(): Flow<TMDBEither<TMDBLatestMovie, String>> =
        flow {
            if(internetVerifier.hasInternetConnection()) {
                val response = api.getLatestMoviesOrSeries(
                    type = TMDBApiInfoType.MOVIES.type,
                    endPoint = TMDBEndPoint.LATEST.endPoint
                )
                response.onStatusListener(
                    success = {
                        saveLatestMovieInDB(it)
                        emit(TMDBEither.Success(it?.toLatestMovie() ?: TMDBLatestMovie()))
                    },
                    failure = { emit(TMDBEither.Failure(it)) }
                )
            } else {
                val movie = getLatestMovie()
                if(movie != null) emit(TMDBEither.Success(movie)) else emit(TMDBEither.Failure(""))
            }
        }.flowOn(dispatcher)
    private suspend fun getGenres() {
        if(!genresGotten) {
            val response = api.getMoviesGenreList()
            if (response.isSuccessful) {
                val data = response.body()
                saveGenresInDB(data)
            }
            genresGotten = true
        }
    }

    /**------------------------------RxJava implementations---------------------------------------*/

    override fun getNowPlayingMoviesRx(): Observable<TMDBMovieSeriesResponse> {
        return api.getMoviesOrSeriesRx(
            type = TMDBApiInfoType.MOVIES.type,
            endPoint = TMDBEndPoint.NOW_PLAYING.endPoint
        ).subscribeOn(scheduler)
    }

    override fun getLatestMoviesRx(): Observable<TMDBLatestMovieResponse> {
        return api.getLatestMoviesOrSeriesRx(
            type = TMDBApiInfoType.MOVIES.type,
            endPoint = TMDBEndPoint.LATEST.endPoint
        ).subscribeOn(scheduler)
    }

    override fun getTopRatedMoviesRx(): Observable<TMDBMovieSeriesResponse> {
        return api.getMoviesOrSeriesRx(
            type = TMDBApiInfoType.MOVIES.type,
            endPoint = TMDBEndPoint.TOP_RATED.endPoint
        ).subscribeOn(scheduler)
    }

    /**------------------------------Data base implementations------------------------------------*/

    override fun getMovieWithGenres(movieId: Int): Flow<TMDBEither<TMDBMovieWithGenres, String>> =
        flow {
            val movieWithGenres = moviesDao.getMovieWithGenres(movieId)
            movieWithGenres?.let {
                emit(TMDBEither.Success(it))
            } ?: run {
                emit(TMDBEither.Failure(""))
            }
        }.flowOn(dispatcher)

    override fun getMoviesInGenre(genreId: Int): Flow<TMDBEither<List<TMDBMoviesInGenre>, String>> =
        flow {
            val movieList = genreDao.getMoviesInGenre(genreId)
            movieList?.let {
                emit(TMDBEither.Success(it))
            } ?: run {
                emit(TMDBEither.Failure(""))
            }
        }.flowOn(dispatcher)

    override fun getMovieById(id: Int): Flow<TMDBEither<TMDBMovie, String>> =
        flow {
            val movie = getMovie(id)
            movie?.let {
                emit(TMDBEither.Success(it))
            } ?: run {
                emit(TMDBEither.Failure(""))
            }
        }.flowOn(dispatcher)

    private fun saveGenresInDB(data: TMDBGenresResponse?) {
        CoroutineScope(dispatcher).launch {
            val insertGenres = async {
                data?.let {
                    val genreList = it.toGenresEntity()
                    genreList.forEach { genre ->
                        genreDao.insertGenre(genre)
                    }
                }
            }
            insertGenres.await()
        }
    }

    private fun saveLatestMovieInDB(data: TMDBLatestMovieResponse?) {
        CoroutineScope(dispatcher).launch {
            val insertLatest = async {
                data?.let {
                    val latestMovie = it.toLatestMovieEntity()
                    latestMovieDao.insertLatestMovie(latestMovie)
                }
            }
            insertLatest.await()
        }
    }
    private suspend fun saveMoviesInDB(data: TMDBMovieSeriesResponse?, type: String) {
        getGenres()
        val insertMovies = coroutineScope {
            async {
                data?.let {
                    val moviesList = it.toMovieEntity(type)
                    moviesList.forEach {movie ->
                        moviesDao.insertMovie(movie)
                    }
                }
            }
        }
        val insertCrossRef = coroutineScope {
            async {
                data?.let {
                    val crossRefList = it.toCrossRefEntity()
                    crossRefList.forEach { crossRef ->
                        crossRefDao.insertCrossRef(crossRef)
                    }
                }
            }
        }
        insertMovies.await()
        insertCrossRef.await()
    }

    private suspend fun getLatestMovie(): TMDBLatestMovie? {
        return withContext(dispatcher) {
            val latestMovie = async { latestMovieDao.getLatestMovie() }
            latestMovie.await()?.toLatestMovie()
        }
    }
    private suspend fun getMovies(type: String): List<TMDBMovie> {
        return withContext(dispatcher) {
            val movies = async { moviesDao.getMovies(type) }
            movies.await().map { it.toMovie() }
        }
    }

    private suspend fun getMovie(id:Int): TMDBMovie? {
        return withContext(dispatcher) {
            val movie = async { moviesDao.getMovie(id) }
            movie.await()?.toMovie()
        }
    }
}

suspend fun <T> Response<T>.onStatusListener(
    success: suspend (T?) -> Unit,
    failure: suspend (String) -> Unit
) {
    if(this.isSuccessful){
        success(this.body())
    } else {
        failure(TMDBApplication.appContext.getString(R.string.generic_error_message))
    }
}