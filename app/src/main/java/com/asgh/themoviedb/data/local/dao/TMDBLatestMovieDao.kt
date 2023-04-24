package com.asgh.themoviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asgh.themoviedb.data.local.entity.TMDBLatestMovieEntity

@Dao
interface TMDBLatestMovieDao {
    @Insert(entity = TMDBLatestMovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestMovie(movie: TMDBLatestMovieEntity)

    @Query("SELECT * FROM latest_movie_table WHERE id LIKE :id")
    fun getLatestMovie(id: Int = 0): TMDBLatestMovieEntity
}