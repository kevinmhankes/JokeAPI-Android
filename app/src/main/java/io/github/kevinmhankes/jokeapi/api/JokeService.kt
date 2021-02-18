package io.github.kevinmhankes.jokeapi.api

import androidx.lifecycle.LiveData
import io.github.kevinmhankes.jokeapi.model.Joke
import io.github.kevinmhankes.jokeapi.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */
interface JokeService {
    /**
     * Fetch jokes from the Programming, Misc, Pun, Spooky, and Christmas categories
     * @param type: always twopart for list-detail view type
     * @param amount: max list of 10 per request
     * @param blacklistFlags: exclude the naughty jokes
     */
    @GET("Programming,Miscellaneous,Pun,Spooky,Christmas")
    fun fetchJokes(
        @Query("type") type: String,
        @Query("amount") amount: Int,
        // Safe mode query does not have a value, so this should always be empty
        @Query("blacklistFlags") blacklistFlags: List<String>
    ): LiveData<ApiResponse<JokeResponse<List<Joke>>>>
}