package com.asgh.themoviedb.commons.converters

import com.squareup.moshi.Moshi

private val moshi = Moshi.Builder().build()

fun <T> toJson(obj: T): String {
    val jsonAdapter = moshi.adapter<T>(obj!!::class.java)
    return jsonAdapter.toJson(obj)
}

fun <T> String.fromJson(type: Class<T>): T? {
    val jsonAdapter = moshi.adapter(type)
    return jsonAdapter.fromJson(this)
}