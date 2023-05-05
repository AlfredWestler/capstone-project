package com.asgh.themoviedb.di

import android.app.Application
import android.content.Context
import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.repository.TMDBRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TMDBModuleBind {

    @Binds
    @Singleton
    abstract fun bindRepository(
        impl: TMDBRepositoryImp
    ): TMDBRepository
}

@InstallIn(SingletonComponent::class)
@Module
object TMDBModule {
    @Provides
    @Singleton
    fun provideContext(
        application: Application
    ): Context = application.applicationContext
}