package io.github.kevinmhankes.jokeapi.dependencyinjection

import android.content.Context
import androidx.room.Room
import io.github.kevinmhankes.jokeapi.persistence.AppDatabase
import io.github.kevinmhankes.jokeapi.util.DATABASE_NAME

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

// Room Database Builder for Koin Injection
fun room(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    DATABASE_NAME,
).build()