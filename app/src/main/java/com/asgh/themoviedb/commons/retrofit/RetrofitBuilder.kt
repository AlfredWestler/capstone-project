package com.asgh.themoviedb.commons.retrofit

import com.asgh.themoviedb.BuildConfig

object RetrofitConstants {
    const val baseUrl = BuildConfig.BASE_URL
    const val connectTimeout: Long = 5000
    const val readTimeout: Long = 5000
}