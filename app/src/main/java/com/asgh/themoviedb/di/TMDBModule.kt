package com.asgh.themoviedb.di

import com.asgh.themoviedb.data.remote.api.TMDBMovieApi
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.repository.TMDBRepository
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

    @Provides
    @Singleton
    fun provideRepository(
        api: TMDBMovieApi,
        dispatcher: CoroutineDispatcher,
        scheduler: Scheduler
    ): TMDBRepository = TMDBRepositoryImp(dispatcher, scheduler, api)

}