package com.asgh.themoviedb.data.remote.api

enum class TMDBEndPoint(val endPoint: String) {
    NOW_PLAYING("now_playing"),
    LATEST("latest"),
    TOP_RATED("top_rated"),
    LIST("list")
}

enum class TMDBApiInfoType(val type: String) {
    MOVIES("movie"),
    TV_SHOWS("tv")
}