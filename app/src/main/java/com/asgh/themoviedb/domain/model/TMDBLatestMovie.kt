package com.asgh.themoviedb.domain.model

data class TMDBLatestMovie(
    val adult: Boolean = false,
    val budget: Int = 0,
    val genres: List<TMDBGenre> = emptyList(),
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Int = 0,
    val poster_path: String = "",
    val release_date: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Int = 0,
    val vote_count: Int = 0,
    val status_message: String = "",
    val status_code: Int = 0
) {

    fun tags(): String {
        var s = if(adult) "Adults · " else ""
        genres.forEachIndexed { index, genre ->
            s += if(index == genres.size - 1){
                genre.name
            } else {
                "${genre.name} · "
            }
        }
        return s
    }
}
