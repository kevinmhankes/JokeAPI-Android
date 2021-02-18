package io.github.kevinmhankes.jokeapi.util

import io.github.kevinmhankes.jokeapi.AppExecutors
import java.util.concurrent.Executor

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * Convenience method for replacing app executors for testing
 */
class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}