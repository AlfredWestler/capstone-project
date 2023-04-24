package com.asgh.themoviedb.data.local.dao

import androidx.room.*
import com.asgh.themoviedb.data.local.entity.TMDBGenreEntity
import com.asgh.themoviedb.data.local.relation.TMDBMoviesInGenre

@Dao
interface TMDBGenreDao {
    @Query("SELECT * FROM genre_table")
    fun getGenres(): List<TMDBGenreEntity>

    @Insert(entity = TMDBGenreEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: TMDBGenreEntity)

    @Transaction
    @Query("SELECT * FROM genre_table WHERE genreId LIKE :genreId")
    fun getMoviesInGenre(genreId: Int): List<TMDBMoviesInGenre>
}