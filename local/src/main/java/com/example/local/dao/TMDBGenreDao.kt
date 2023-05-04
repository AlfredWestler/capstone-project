package com.example.local.dao

import androidx.room.*
import com.example.local.entity.TMDBGenreEntity
import com.example.local.relation.TMDBMoviesInGenre

@Dao
interface TMDBGenreDao {
    @Query("SELECT * FROM genre_table")
    fun getGenres(): List<TMDBGenreEntity>

    @Insert(entity = TMDBGenreEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: TMDBGenreEntity)

    @Transaction
    @Query("SELECT * FROM genre_table WHERE genreId LIKE :genreId")
    fun getMoviesInGenre(genreId: Int): List<TMDBMoviesInGenre>?
}