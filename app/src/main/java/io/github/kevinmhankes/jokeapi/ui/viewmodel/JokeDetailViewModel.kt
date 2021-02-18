package io.github.kevinmhankes.jokeapi.ui.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.kevinmhankes.jokeapi.persistence.JokeRepository
import io.github.kevinmhankes.jokeapi.testing.OpenForTesting

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * ViewModel backing the JokeDetailFragment for loading the joke detail information
 */
@OpenForTesting
class JokeDetailViewModel(
    jokeId: Int,
    jokeRepository: JokeRepository
) : ViewModel() {

    // Get the joke data from the repository
    private val joke = jokeRepository.getJoke(jokeId)

    // Map the joke data to the proper fields in the JokeDetailView
    val jokeDetail = Transformations.map(joke) {
        JokeDetailView(
            id = it.id,
            category = it.category,
            setup = it.setup,
            delivery = it.delivery
        )
    }
}

/**
 * View of joke detail to display on the joke details fragment from the joke API
 */
data class JokeDetailView(
    val id: Int?,
    val category: String?,
    val setup: String?,
    val delivery: String?
)