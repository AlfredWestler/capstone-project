package com.asgh.themoviedb.di

import com.asgh.themoviedb.data.repository.TMDBRepositoryImp
import com.asgh.themoviedb.domain.repository.TMDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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