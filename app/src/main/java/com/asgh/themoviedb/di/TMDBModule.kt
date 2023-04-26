package com.asgh.themoviedb.di

import android.app.Application
import com.asgh.themoviedb.commons.internet.ConnectionVerifier
import com.asgh.themoviedb.commons.internet.InternetConnectionVerifier
import com.asgh.themoviedb.data.local.dao.TMDBCrossRefDao
import com.asgh.themoviedb.data.local.dao.TMDBGenreDao
import com.asgh.themoviedb.data.local.dao.TMDBLatestMovieDao
import com.asgh.themoviedb.data.local.dao.TMDBMoviesDao
import com.asgh.themoviedb.data.remote.api.TMDBMovieApi
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.repository.TMDBRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TMDBModule {

    /*@Provides
    @Singleton
    fun provideRepository(
        api: TMDBMovieApi,
        dispatcher: CoroutineDispatcher,
        scheduler: Scheduler,
        moviesDao: TMDBMoviesDao,
        genreDao: TMDBGenreDao,
        latestMovieDao: TMDBLatestMovieDao,
        crossRefDao: TMDBCrossRefDao,
        internetVerifier: ConnectionVerifier
    ): TMDBRepository = TMDBRepositoryImp(
        dispatcher,
        scheduler,
        api,
        moviesDao,
        genreDao,
        latestMovieDao,
        crossRefDao,
        internetVerifier
    )*/
}

@InstallIn(SingletonComponent::class)
@Module
abstract class TMDBModuleBind {

    @Binds
    @Singleton
    abstract fun bindRepository(
        impl: TMDBRepositoryImp
    ): TMDBRepository
}