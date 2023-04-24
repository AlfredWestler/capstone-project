package com.asgh.themoviedb.data.local.dao

import androidx.room.*
import com.asgh.themoviedb.data.local.entity.TMDBMovieEntity
import com.asgh.themoviedb.data.local.relation.TMDBMovieWithGenres
import com.asgh.themoviedb.data.local.relation.TMDBMoviesInGenre

@Dao
interface TMDBMoviesDao {

    @Query("SELECT * FROM movie_table WHERE type LIKE :selectedType")
    fun getMovies(selectedType: String): List<TMDBMovieEntity>

    @Insert(entity = TMDBMovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: TMDBMovieEntity)

    @Transaction
    @Query("SELECT * FROM movie_table WHERE movieId LIKE :movieId")
    fun getMovieWithGenres(movieId: Int): TMDBMovieWithGenres
}