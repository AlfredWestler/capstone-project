package com.example.local.dao

import androidx.room.*
import com.example.local.entity.TMDBMovieEntity
import com.example.local.relation.TMDBMovieWithGenres

@Dao
interface TMDBMoviesDao {

    @Query("SELECT * FROM movie_table WHERE type LIKE :selectedType")
    fun getMovies(selectedType: String): List<TMDBMovieEntity>

    @Query("SELECT * FROM movie_table WHERE movieId LIKE :id")
    fun getMovie(id: Int): TMDBMovieEntity?

    @Insert(entity = TMDBMovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: TMDBMovieEntity)

    @Transaction
    @Query("SELECT * FROM movie_table WHERE movieId LIKE :movieId")
    fun getMovieWithGenres(movieId: Int): TMDBMovieWithGenres?
}