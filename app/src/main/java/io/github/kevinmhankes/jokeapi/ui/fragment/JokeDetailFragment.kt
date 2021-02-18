package io.github.kevinmhankes.jokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.kevinmhankes.jokeapi.R
import io.github.kevinmhankes.jokeapi.databinding.JokeDetailFragmentBinding
import io.github.kevinmhankes.jokeapi.testing.OpenForTesting
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * Fragment for displaying joke details after clicking on it in the list
 * This fragment gets a jokeId passed to it through [navArgs], which is then used to
 * pull the details of that joke from the database
 */
@OpenForTesting
class JokeDetailFragment : Fragment() {

    // Argument is declared in the nav_graph.xml for the JokeDetailFragment
    private val args: JokeDetailFragmentArgs by navArgs()

    private val jokeDetailViewModel: JokeDetailViewModel by viewModel { parametersOf(args.jokeId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<JokeDetailFragmentBinding>(
            inflater,
            R.layout.joke_detail_fragment,
            container,
            false
        ).apply {
            viewModel = jokeDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}