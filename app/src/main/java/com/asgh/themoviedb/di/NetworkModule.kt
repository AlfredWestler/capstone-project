package com.asgh.themoviedb.di

import com.asgh.themoviedb.commons.retrofit.RetrofitConstants
import com.asgh.themoviedb.commons.retrofit.TMDBInterceptor
import com.asgh.themoviedb.data.remote.api.TMDBMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovieApi(
        retrofit: Retrofit
    ): TMDBMovieApi = retrofit.create(TMDBMovieApi::class.java)

     @Provides
     @Singleton
     fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
         return Retrofit.Builder()
             .baseUrl(RetrofitConstants.baseUrl)
             .addConverterFactory(MoshiConverterFactory.create())
             .client(okHttpClient)
             .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
             .build()

     }

    @Provides
    @Singleton
    fun getOkHttpClient(interceptor: TMDBInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(RetrofitConstants.connectTimeout, TimeUnit.SECONDS)
            .readTimeout(RetrofitConstants.readTimeout, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor() = TMDBInterceptor()

}