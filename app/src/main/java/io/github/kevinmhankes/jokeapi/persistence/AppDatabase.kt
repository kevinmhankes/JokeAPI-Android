package io.github.kevinmhankes.jokeapi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.kevinmhankes.jokeapi.model.Joke

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Main Database Description
 */
@Database(
    entities = [Joke::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}