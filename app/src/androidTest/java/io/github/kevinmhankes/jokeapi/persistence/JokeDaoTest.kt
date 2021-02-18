package io.github.kevinmhankes.jokeapi.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.kevinmhankes.jokeapi.model.Joke
import io.github.kevinmhankes.jokeapi.util.LiveDataTestUtil.getValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Kevin.
 * Created/Modified on February 16, 2021
 */

@RunWith(AndroidJUnit4::class)
class JokeDaoTest : DatabaseTestBase() {
    private val joke1 = Joke(1, "Spooky", "Why did the ghost go inside the bar?", "For the boos.")
    private val joke2 = Joke(2, "Programming", "Why does no one like SQLrillex?", "He keeps dropping the database.")
    private val joke3 = Joke(3, "Pun", "What kind of doctor is Dr. Pepper?", "He's a fizzician")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetJokes() {
        database.jokeDao().insertAllJokes(listOf(joke1, joke2, joke3))

        val jokesFromDatabase = database.jokeDao().getJokes()
        assertThat(getValue(jokesFromDatabase)[0], `is`(joke1))
        assertThat(getValue(jokesFromDatabase)[1], `is`(joke2))
        assertThat(getValue(jokesFromDatabase)[2], `is`(joke3))

    }

    @Test
    fun testGetJoke() {
        database.jokeDao().insertAllJokes(listOf(joke1, joke2, joke3))

        assertThat(getValue(database.jokeDao().getJoke(joke3.id)), `is`(joke3))
    }
}