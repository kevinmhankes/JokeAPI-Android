package io.github.kevinmhankes.jokeapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * This Application is used to run unit tests with in order to prevent dependency injection
 * and instead only inject the necessary dependencies in each test case
 */
class TestMainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestMainApplication)
        }
    }
}