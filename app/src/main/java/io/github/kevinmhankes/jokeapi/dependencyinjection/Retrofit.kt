package io.github.kevinmhankes.jokeapi.dependencyinjection

import io.github.kevinmhankes.jokeapi.api.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

// Retrofit Builder for Koin Injection
fun retrofit(
    baseUrl: String,
    converterFactory: Converter.Factory,
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory)
    .addCallAdapterFactory(LiveDataCallAdapterFactory())
    .build()