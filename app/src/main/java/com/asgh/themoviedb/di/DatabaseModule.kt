package com.asgh.themoviedb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.asgh.themoviedb.data.local.TMDBDatabase
import com.asgh.themoviedb.data.local.dao.TMDBCrossRefDao
import com.asgh.themoviedb.data.local.dao.TMDBGenreDao
import com.asgh.themoviedb.data.local.dao.TMDBLatestMovieDao
import com.asgh.themoviedb.data.local.dao.TMDBMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): TMDBDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            TMDBDatabase::class.java,
            "one_to_one_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: TMDBDatabase): TMDBMoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideGenreDao(database: TMDBDatabase): TMDBGenreDao {
        return database.genreDao()
    }

    @Provides
    @Singleton
    fun providesLatestMovieDao(database: TMDBDatabase): TMDBLatestMovieDao {
        return database.latestMovieDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefDao(database: TMDBDatabase): TMDBCrossRefDao {
        return database.crossRefDao()
    }
}