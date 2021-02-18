package io.github.kevinmhankes.jokeapi.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.kevinmhankes.jokeapi.databinding.ListItemJokeBinding
import io.github.kevinmhankes.jokeapi.ui.fragment.JokeListFragmentDirections
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeItemView

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * RecyclerView adapter to hold the list of joke setups
 * Sets onClick to navigate to the joke details for the joke delivery
 */
class JokeAdapter : ListAdapter<JokeItemView, RecyclerView.ViewHolder>(JokeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JokeViewHolder(
            ListItemJokeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val jokeItemView = getItem(position)
        (holder as JokeViewHolder).bind(jokeItemView)
    }

    /**
     * Subclassed RecyclerView.ViewHolder customized for the joke list view
     */
    class JokeViewHolder(
        private val binding: ListItemJokeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                navigateToJoke(binding.joke, it)
            }
        }

        private fun navigateToJoke(
            joke: JokeItemView?,
            view: View
        ) {
            val direction = joke?.id?.let {
                JokeListFragmentDirections.actionJokeListFragmentToJokeDetailFragment(
                    it
                )
            }
            direction?.let { view.findNavController().navigate(it) }
        }

        fun bind(item: JokeItemView) {
            binding.apply {
                joke = item
                executePendingBindings()
            }
        }
    }
}

/**
 * Class to determine if joke items/contents are the same for the purposes of a RecyclerView
 */
private class JokeDiffCallback : DiffUtil.ItemCallback<JokeItemView>() {
    override fun areItemsTheSame(oldItem: JokeItemView, newItem: JokeItemView): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JokeItemView, newItem: JokeItemView): Boolean {
        return oldItem.id == newItem.id
    }

}