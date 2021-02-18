package io.github.kevinmhankes.jokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.kevinmhankes.jokeapi.binding.JokeAdapter
import io.github.kevinmhankes.jokeapi.databinding.JokeListFragmentBinding
import io.github.kevinmhankes.jokeapi.testing.OpenForTesting
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Fragment displaying a list of joke setups utilizing the [JokeAdapter] to bind to the RecyclerView
 * Also adds an onClick to each joke setup in order to navigate to the joke details
 * The jokeId is passed through navArgs to provide the JokeDetailViewModel with the joke to lookup
 * While the jokes are being loaded, a progress bar will be visible until the loading has finished
 */
@OpenForTesting
class JokeListFragment : Fragment() {

    private val listViewModel: JokeListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = JokeListFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = JokeAdapter()
        binding.jokeList.adapter = adapter
        subscribeUI(adapter)

        listViewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    private fun subscribeUI(adapter: JokeAdapter) {
        listViewModel.jokeInfo.observe(viewLifecycleOwner) { it ?: return@observe
            adapter.submitList(it)
        }
    }
}