package com.example.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.dao.TMDBCrossRefDao
import com.example.local.dao.TMDBGenreDao
import com.example.local.dao.TMDBLatestMovieDao
import com.example.local.dao.TMDBMoviesDao
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBLatestMovieEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.entity.TMDBMovieGenreCrossRefEntity

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