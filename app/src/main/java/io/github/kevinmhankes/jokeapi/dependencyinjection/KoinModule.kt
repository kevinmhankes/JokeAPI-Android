package io.github.kevinmhankes.jokeapi.dependencyinjection

import com.google.gson.Gson
import io.github.kevinmhankes.jokeapi.AppExecutors
import io.github.kevinmhankes.jokeapi.api.JokeService
import io.github.kevinmhankes.jokeapi.persistence.AppDatabase
import io.github.kevinmhankes.jokeapi.persistence.JokeRepository
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeDetailViewModel
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeListViewModel
import io.github.kevinmhankes.jokeapi.util.BASE_URL
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * Module defining dependencies to be available via koin dependency injection
 */
val koinModule = module {

    // Gson Instance and Converter Factory for Retrofit
    single { Gson() }
    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    // OkHttp Instance
    single { okHttp(androidApplication()) }

    // Retrofit Instance
    single { retrofit(BASE_URL, get(), get()) }

    // JokeService HTTP Interface
    single<JokeService> { get<Retrofit>().create(JokeService::class.java) }

    // Joke Database
    single { room(androidApplication()) }

    // Joke DAO Instance
    single { get<AppDatabase>().jokeDao() }

    // App Executors
    single { AppExecutors() }

    // Joke Repository
    single { JokeRepository(get(), get(), get()) }

    // View Models
    viewModel { JokeListViewModel(get()) }
    viewModel { (jokeId: Int) -> JokeDetailViewModel(jokeId, get()) }
}
