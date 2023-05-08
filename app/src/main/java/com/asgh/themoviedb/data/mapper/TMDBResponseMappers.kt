package com.asgh.themoviedb.data.mapper

import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.example.local.entity.TMDBGenreEntity
import com.example.local.entity.TMDBLatestMovieEntity
import com.example.local.entity.TMDBMovieEntity
import com.example.local.entity.TMDBMovieGenreCrossRefEntity
import com.example.remote.response.TMDBGenresResponse
import com.example.remote.response.TMDBLatestMovieResponse
import com.example.remote.response.TMDBMovieSeriesResponse

fun TMDBGenresResponse.toGenresEntity(): List<TMDBGenreEntity> {
    return genres?.map { it.toGenreEntity() } ?: emptyList()
}

fun TMDBLatestMovieResponse.toLatestMovie() = TMDBLatestMovie(
    adult = adult ?: false,
    budget = budget ?: 0,
    genres = genres?.map { it.toGenre() }?.toList() ?: emptyList(),
    homepage = homepage.orEmpty(),
    id = id ?: 0,
    imdb_id = imdb_id.orEmpty(),
    original_language = original_language.orEmpty(),
    original_title = original_title.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0,
    poster_path = "${BuildConfig.IMAGE_URL}$poster_path",
    release_date = release_date.orEmpty(),
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    status = status.orEmpty(),
    tagline = tagline.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    vote_average = vote_average ?: 0,
    vote_count = vote_count ?: 0,
    status_message = status_message.orEmpty(),
    status_code = status_code ?: 0
)

fun TMDBLatestMovieResponse.toLatestMovieEntity() = TMDBLatestMovieEntity(
    id = 0,
    adult = adult ?: false,
    budget = budget ?: 0,
    homepage = homepage.orEmpty(),
    movieId = id ?: 0,
    imdb_id = imdb_id.orEmpty(),
    original_language = original_language.orEmpty(),
    original_title = original_title.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0,
    poster_path = "${BuildConfig.IMAGE_URL}$poster_path",
    release_date = release_date.orEmpty(),
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    status = status.orEmpty(),
    tagline = tagline.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    vote_average = vote_average ?: 0,
    vote_count = vote_count ?: 0,
    status_message = status_message.orEmpty(),
    status_code = status_code ?: 0
)

fun TMDBMovieSeriesResponse.toMovies(): List<TMDBMovie> {
    return results?.map { it.toMovie() } ?: emptyList()
}

fun TMDBMovieSeriesResponse.toMovieEntity(type: String): List<TMDBMovieEntity> {
    //type is now_playing or top_rated
    return results?.map {
        it.transformToEntity(type)
    } ?: emptyList()
}

fun TMDBMovieSeriesResponse.toCrossRefEntity(): List<TMDBMovieGenreCrossRefEntity> {
    val aux = mutableListOf<TMDBMovieGenreCrossRefEntity>()
    results?.forEach {movie ->
        movie.genre_ids?.forEach {genreId ->
            aux.add(
                TMDBMovieGenreCrossRefEntity(
                    genreId = genreId,
                    movieId = movie.id ?: 0
                )
            )
        }
    }
    return aux
}