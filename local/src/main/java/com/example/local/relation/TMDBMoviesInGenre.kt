package com.example.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.entity.TMDBMovieGenreCrossRefEntity

data class TMDBMoviesInGenre(
    @Embedded val genre: TMDBGenreEntity,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        entity = TMDBMovieEntity::class,
        associateBy = Junction(TMDBMovieGenreCrossRefEntity::class)
    ) val movies: List<TMDBMovieEntity>
)
