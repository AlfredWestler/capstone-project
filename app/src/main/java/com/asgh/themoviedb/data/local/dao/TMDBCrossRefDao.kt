package com.asgh.themoviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.asgh.themoviedb.data.local.entity.TMDBMovieGenreCrossRefEntity

@Dao
interface TMDBCrossRefDao {
    @Insert(entity = TMDBMovieGenreCrossRefEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossReference: TMDBMovieGenreCrossRefEntity)
}