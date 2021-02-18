package io.github.kevinmhankes.jokeapi.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.github.kevinmhankes.jokeapi.R
import io.github.kevinmhankes.jokeapi.ui.fragment.JokeDetailFragment
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeDetailView
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeDetailViewModel
import io.github.kevinmhankes.jokeapi.util.InjectKoinModuleRule
import io.github.kevinmhankes.jokeapi.util.JOKE_ID
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */
@RunWith(AndroidJUnit4::class)
class JokeDetailFragmentTest {
    private lateinit var scenario: FragmentScenario<JokeDetailFragment>

    private val mockJokeDetail = MutableLiveData<JokeDetailView>()
    private val bundle = Bundle()

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    companion object {
        lateinit var mockViewModel: JokeDetailViewModel

        @ClassRule
        @JvmField
        val koinRule = InjectKoinModuleRule(
            module {
                viewModel { mockViewModel }
            }
        )
    }

    @Before
    fun setUp() {
        bundle.putInt(JOKE_ID, 1)
        mockViewModel = mock {
            on { jokeDetail } doReturn mockJokeDetail
        }
        mockJokeDetail.postValue(JokeDetailView(1, "Spooky","Why did the ghost go inside the bar?", "For the Boos."))
        scenario = launchFragmentInContainer(fragmentArgs = bundle)
    }

    @Test
    fun testJokeDetail() {
        Espresso.onView(ViewMatchers.withId(R.id.joke_category))
            .check(matches(withText("Spooky")))
        Espresso.onView(ViewMatchers.withId(R.id.joke_setup))
            .check(matches(withText("Why did the ghost go inside the bar?")))
        Espresso.onView(ViewMatchers.withId(R.id.joke_delivery))
            .check(matches(withText("For the Boos.")))
    }
}