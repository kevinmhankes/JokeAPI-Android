package io.github.kevinmhankes.jokeapi.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import io.github.kevinmhankes.jokeapi.api.JokeService
import io.github.kevinmhankes.jokeapi.api.util.Resource
import io.github.kevinmhankes.jokeapi.model.Joke
import io.github.kevinmhankes.jokeapi.model.JokeResponse
import io.github.kevinmhankes.jokeapi.util.ApiUtil
import io.github.kevinmhankes.jokeapi.util.InstantAppExecutors
import io.github.kevinmhankes.jokeapi.util.RATE_LIMITER_KEY
import io.github.kevinmhankes.jokeapi.util.testJokes
import okhttp3.internal.immutableListOf
import org.junit.Rule
import org.junit.Test

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * Still a work in progress.
 */
class JokeRepositoryTest {
    private val jokeDao = mock<JokeDao>()
    private val jokeService = mock<JokeService>()
    private val jokeRepository = JokeRepository(
        InstantAppExecutors(),
        jokeDao,
        jokeService
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getJokesFromNetwork() {
        val dbData = MutableLiveData<List<Joke>>()
        val updatedDbData = MutableLiveData<List<Joke>>()
        whenever(jokeDao.getJokes()) doReturnConsecutively listOf(dbData, updatedDbData)

        val callSuccessResponse = ApiUtil.successCall(
            JokeResponse(
                jokes = testJokes.value ?: emptyList()
            )
        )

        whenever(
            jokeService.fetchJokes(
                "twopart",
                10,
                immutableListOf(
                    "nsfw",
                    "religious",
                    "political",
                    "racist",
                    "sexist",
                    "explicit")
            )
        ) doReturn callSuccessResponse

        val observer = mock<Observer<Resource<List<Joke>>>>()
        jokeRepository.getJokes(RATE_LIMITER_KEY).observeForever(observer)

        verify(jokeService, never()).fetchJokes(
            "twopart",
            10,
            immutableListOf(
                "nsfw",
                "religious",
                "political",
                "racist",
                "sexist",
                "explicit"))
    }
}