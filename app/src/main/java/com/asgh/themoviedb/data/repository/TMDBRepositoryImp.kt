package com.asgh.themoviedb.data.repository

import com.asgh.themoviedb.data.remote.api.TMDBApiInfoType
import com.asgh.themoviedb.data.remote.api.TMDBEndPoint
import com.asgh.themoviedb.data.remote.api.TMDBMovieApi
import com.asgh.themoviedb.data.remote.response.TMDBLatestMovieResponse
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.repository.TMDBRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TMDBRepositoryImp @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val scheduler: Scheduler,
    private val api: TMDBMovieApi
): TMDBRepository {

    override suspend fun getNowPlayingMovies(): Response<TMDBMovieSeriesResponse> =
        withContext(dispatcher) {
            api.getMoviesOrSeries(
                type = TMDBApiInfoType.MOVIES.type,
                endPoint = TMDBEndPoint.NOW_PLAYING.endPoint
            )
        }

    override suspend fun getLatestMovies(): Response<TMDBLatestMovieResponse> =
        withContext(dispatcher) {
            api.getLatestMoviesOrSeries(
                type = TMDBApiInfoType.MOVIES.type,
                endPoint = TMDBEndPoint.LATEST.endPoint
            )
        }

    override suspend fun getTopRatedMovies(): Response<TMDBMovieSeriesResponse> =
        withContext(dispatcher) {
            api.getMoviesOrSeries(
                type = TMDBApiInfoType.MOVIES.type,
                endPoint = TMDBEndPoint.TOP_RATED.endPoint
            )
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
}