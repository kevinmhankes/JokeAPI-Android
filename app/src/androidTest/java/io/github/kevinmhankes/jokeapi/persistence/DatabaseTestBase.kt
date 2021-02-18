package io.github.kevinmhankes.jokeapi.persistence

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
* A base class to extend for all of our database instrumented tests on the VelocityDatabase.
* Provides the initialization and closing of an in memory database.
* Access the database in subclass test methods via the [database] property.
*/
abstract class DatabaseTestBase {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private lateinit var _database: AppDatabase
    val database: AppDatabase
        get() = _database

    /**
     * Create an in memory database to run DAO unit tests against.
     * Database exposed to subclasses via the [database] property.
     */
    @Before
    fun initDatabase() {
        _database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    /**
     * Wait 10 seconds max for background tasks to complete and then close the database.
     */
    @After
    fun closeDatabase() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _database.close()
    }

}