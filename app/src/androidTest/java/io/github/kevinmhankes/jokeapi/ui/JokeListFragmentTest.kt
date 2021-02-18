package io.github.kevinmhankes.jokeapi.ui

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.github.kevinmhankes.jokeapi.R
import io.github.kevinmhankes.jokeapi.ui.fragment.JokeListFragment
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeItemView
import io.github.kevinmhankes.jokeapi.ui.viewmodel.JokeListViewModel
import io.github.kevinmhankes.jokeapi.util.InjectKoinModuleRule
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

@RunWith(AndroidJUnit4::class)
class JokeListFragmentTest {
    private lateinit var scenario: FragmentScenario<JokeListFragment>

    private val mockJokeList = MutableLiveData<List<JokeItemView>>()
    private val loading = MutableLiveData<Boolean>()

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    companion object {
        lateinit var mockViewModel: JokeListViewModel

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
        mockViewModel = mock {
            on { jokeInfo } doReturn mockJokeList
            on { loading } doReturn loading
        }
        scenario = launchFragmentInContainer()
    }

    @Test
    fun testJokeListSetup() {
        onView(withId(R.id.joke_list)).check(matches(hasChildCount(0)))

        // one item in list
        mockJokeList.postValue((listOf(JokeItemView(1, "Why did the ghost go inside the bar?"))))
        onView(withId(R.id.joke_list_item_setup)).check(matches(withText("Why did the ghost go inside the bar?")))
    }

    @Test
    fun loading() {
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        loading.postValue(true)
        countingTaskExecutorRule.drainTasks(3, TimeUnit.SECONDS)
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }
}