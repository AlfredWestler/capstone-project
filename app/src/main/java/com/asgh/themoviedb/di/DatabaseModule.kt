package com.asgh.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.example.local.TMDBDatabase
import com.example.local.dao.TMDBCrossRefDao
import com.example.local.dao.TMDBGenreDao
import com.example.local.dao.TMDBLatestMovieDao
import com.example.local.dao.TMDBMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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