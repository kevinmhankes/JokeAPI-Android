
/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * Class structure adapted from PlantAdapter:
 * https://github.com/android/sunflower
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