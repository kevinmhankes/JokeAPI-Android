package io.github.kevinmhankes.jokeapi

import android.app.Application
import androidx.viewbinding.BuildConfig
import io.github.kevinmhankes.jokeapi.dependencyinjection.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Main application used for starting system resources like Koin and Timber
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            // Work around an issue with Koin logging until
            // the 2.1.6 issue is fixed
            // See https://github.com/InsertKoinIO/koin/issues/847
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(koinModule)
        }
    }
}