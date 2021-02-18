package io.github.kevinmhankes.jokeapi.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.kevinmhankes.jokeapi.model.Joke

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Interface for database access for Joke related operations
 */
@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes")
    fun getJokes(): LiveData<List<Joke>>

    @Query("SELECT * FROM jokes WHERE id = :id")
    fun getJoke(id: Int): LiveData<Joke>

    @Transaction
    fun deleteAndCreate(jokes: List<Joke>) {
        deleteAllJokes()
        insertAllJokes(jokes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllJokes(jokes: List<Joke>)

    @Query("DELETE FROM jokes")
    fun deleteAllJokes()
}