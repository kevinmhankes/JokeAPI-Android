package io.github.kevinmhankes.jokeapi.api.util

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * A generic class that holds a value with its loading status.
 *
 * @param T the data type wrapped by this Resource
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}