
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

import androidx.lifecycle.LiveData
import io.github.kevinmhankes.jokeapi.AppExecutors
import io.github.kevinmhankes.jokeapi.api.ApiResponse
import io.github.kevinmhankes.jokeapi.api.JokeService
import io.github.kevinmhankes.jokeapi.api.util.NetworkBoundResource
import io.github.kevinmhankes.jokeapi.api.util.Resource
import io.github.kevinmhankes.jokeapi.model.Joke
import io.github.kevinmhankes.jokeapi.model.JokeResponse
import io.github.kevinmhankes.jokeapi.testing.OpenForTesting
import io.github.kevinmhankes.jokeapi.util.JOKE_SERVICE_RATE_LIMIT_TIME
import io.github.kevinmhankes.jokeapi.util.RateLimiter
import okhttp3.internal.immutableListOf

/**
 * @author Kevin.
 * Class influenced by NetworkBoundResource abstract class from GithubBrowserSample
 * https://github.com/android/architecture-components-samples
 */

/**
 * Repository that handles Joke instances
 */
@OpenForTesting
class JokeRepository(
    private val appExecutors: AppExecutors,
    private val jokeDao: JokeDao,
    private val jokeService: JokeService
) {

    private val jokeServiceRateLimiter = RateLimiter<String>(
        JOKE_SERVICE_RATE_LIMIT_TIME,
        java.util.concurrent.TimeUnit.MINUTES
    )

    fun getJokes(key: String): LiveData<Resource<List<Joke>>> {
        return object : NetworkBoundResource<List<Joke>, JokeResponse<List<Joke>>>(appExecutors) {
            override fun saveCallResult(item: JokeResponse<List<Joke>>) {
                jokeDao.deleteAndCreate(item.jokes)
            }

            override fun shouldFetch(data: List<Joke>?): Boolean {
                return data == null || data.isEmpty() || jokeServiceRateLimiter.shouldFetch(key)
            }

            override fun loadFromDb() = jokeDao.getJokes()

            override fun createCall(): LiveData<ApiResponse<JokeResponse<List<Joke>>>> = jokeService.fetchJokes("twopart", 10, immutableListOf("nsfw", "religious", "political", "racist", "sexist", "explicit"))

            override fun onFetchFailed() {
                super.onFetchFailed()
                jokeServiceRateLimiter.reset(key)
            }
        }.asLiveData()
    }

    // Used for the detail view
    fun getJoke(id: Int) = jokeDao.getJoke(id)
}