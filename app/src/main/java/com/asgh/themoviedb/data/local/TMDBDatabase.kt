package com.asgh.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asgh.themoviedb.data.local.dao.TMDBCrossRefDao
import com.asgh.themoviedb.data.local.dao.TMDBGenreDao
import com.asgh.themoviedb.data.local.dao.TMDBLatestMovieDao
import com.asgh.themoviedb.data.local.dao.TMDBMoviesDao
import com.asgh.themoviedb.data.local.entity.TMDBGenreEntity
import com.asgh.themoviedb.data.local.entity.TMDBLatestMovieEntity
import com.asgh.themoviedb.data.local.entity.TMDBMovieEntity
import com.asgh.themoviedb.data.local.entity.TMDBMovieGenreCrossRefEntity

@Database(
    entities = [
        TMDBMovieEntity::class,
        TMDBGenreEntity::class,
        TMDBMovieGenreCrossRefEntity::class,
        TMDBLatestMovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun moviesDao(): TMDBMoviesDao
    abstract fun genreDao(): TMDBGenreDao
    abstract fun latestMovieDao(): TMDBLatestMovieDao
    abstract fun crossRefDao(): TMDBCrossRefDao
}