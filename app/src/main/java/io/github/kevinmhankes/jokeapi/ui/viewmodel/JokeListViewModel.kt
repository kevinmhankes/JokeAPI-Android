package io.github.kevinmhankes.jokeapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.kevinmhankes.jokeapi.api.util.Status
import io.github.kevinmhankes.jokeapi.persistence.JokeRepository
import io.github.kevinmhankes.jokeapi.testing.OpenForTesting
import io.github.kevinmhankes.jokeapi.ui.MultiSourceLoadingLiveData
import io.github.kevinmhankes.jokeapi.util.RATE_LIMITER_KEY

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * ViewModel backing the JokeListFragment for loading loan and loan detail info
 */
@OpenForTesting
class JokeListViewModel(
    jokeRepository: JokeRepository
) : ViewModel() {

    /**
     * Get the joke list from the repository
     */
    private val jokes = jokeRepository.getJokes(
        RATE_LIMITER_KEY
    )

    /**
     * LiveData of jokes loaded
     */
    val jokeInfo = Transformations.map(jokes) {
        when (it.status) {
            Status.LOADING -> null
            Status.ERROR -> null
            Status.SUCCESS -> {
                val jokeResponses = it.data
                jokeResponses?.map { jokeResponse ->
                    JokeItemView(
                        id = jokeResponse.id,
                        jokeSetup = jokeResponse.setup
                    )
                }
            }
        }
    }

    /**
     * The loading status of the list of jokes
     */
    val loading: LiveData<Boolean> = MultiSourceLoadingLiveData(listOf(jokes))
}

/**
 * View of info to display on the joke list fragment from the joke API
 */
data class JokeItemView(
    var id: Int?,
    var jokeSetup: String?
)