package com.example.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.entity.TMDBMovieGenreCrossRefEntity

data class TMDBMovieWithGenres(
    @Embedded val movie: TMDBMovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(TMDBMovieGenreCrossRefEntity::class)
    ) val genres: List<TMDBGenreEntity>
) {

    fun getGenresString(): String {
        var aux = ""
        genres.forEachIndexed { index, it ->
            aux += if(index == genres.size -1){
                it.genreName.orEmpty()
            } else {
                "${it.genreName.orEmpty()}, "
            }
        }
        return aux
    }
}