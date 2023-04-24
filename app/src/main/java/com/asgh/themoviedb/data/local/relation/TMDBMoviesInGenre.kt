package com.asgh.themoviedb.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.asgh.themoviedb.data.local.entity.TMDBGenreEntity
import com.asgh.themoviedb.data.local.entity.TMDBMovieEntity
import com.asgh.themoviedb.data.local.entity.TMDBMovieGenreCrossRefEntity

data class TMDBMoviesInGenre(
    @Embedded val genre: TMDBGenreEntity,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        entity = TMDBMovieEntity::class,
        associateBy = Junction(TMDBMovieGenreCrossRefEntity::class)
    ) val movies: List<TMDBMovieEntity>
)
