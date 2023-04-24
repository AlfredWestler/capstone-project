package com.asgh.themoviedb.commons.retrofit

import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.TMDBApplication
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.*

class TMDBInterceptor: Interceptor {

    private val apiKey = BuildConfig.API_KEY
    private val language = TMDBApplication.appContext.resources.configuration.locales.get(0).language
    private val region = Locale.getDefault().country

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        if(!originalUrl.toString().contains("latest")){
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter(TMDBInterceptorQuery.API_KEY.query, apiKey)
                .addQueryParameter(TMDBInterceptorQuery.LANGUAGE.query, language)
                .addQueryParameter(TMDBInterceptorQuery.REGION.query, region)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            val newResponse = chain.proceed(newRequest)
            Timber.d("Retrofit request: ${newRequest.method}\n${newRequest.url}")
            Timber.d("Retrofit response: ${newResponse.code}\n${newResponse.networkResponse}")
            return newResponse
        } else {
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter(TMDBInterceptorQuery.API_KEY.query, apiKey)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            return chain.proceed(newRequest)
        }
    }
}

enum class TMDBInterceptorQuery(val query: String) {
    API_KEY("api_key"),
    LANGUAGE("language"),
    REGION("region")
}