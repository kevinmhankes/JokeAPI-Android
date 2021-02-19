
/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.kevinmhankes.jokeapi.persistence

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
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