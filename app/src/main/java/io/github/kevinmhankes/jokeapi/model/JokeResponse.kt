package io.github.kevinmhankes.jokeapi.model

import com.google.gson.annotations.SerializedName

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Top level JSON response from the joke API, we really just want the list of jokes, so no need
 * to save this to a table
 */
data class JokeResponse<T>(
    @field:SerializedName("jokes") val jokes: T
)