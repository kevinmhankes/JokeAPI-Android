package io.github.kevinmhankes.jokeapi.dependencyinjection

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

// OkHttp Builder for Koin Injection
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun okHttp(context: Context): OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(loggingInterceptor)
    .build()