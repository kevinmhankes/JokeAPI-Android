package io.github.kevinmhankes.jokeapi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.github.kevinmhankes.jokeapi.api.util.Resource
import io.github.kevinmhankes.jokeapi.api.util.Status
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */
@RunWith(Parameterized::class)
class MultiSourceLoadingLiveDataTest(
    private val source1Status: Status,
    private val source2Status: Status,
    private val output: Boolean
) {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test() {
        val source1 = MutableLiveData<Resource<Int>>().apply {
            value = Resource(source1Status, null, null)
        }
        val source2 = MutableLiveData<Resource<Int>>().apply {
            value = Resource(source2Status, null, null)
        }

        val observer = mock<Observer<Boolean>>()

        MultiSourceLoadingLiveData(
            listOf(source1, source2),
        ).apply {
            observeForever(observer)
        }

        verify(observer).onChanged(output)
    }

    companion object {
        @Parameterized.Parameters(name = "{0}, {1} -> {4}")
        @JvmStatic
        fun params(): List<Array<out Any?>> =
            listOf(
                // status 1, status 2, expected output
                arrayOf(Status.LOADING, Status.LOADING, true),
                arrayOf(Status.LOADING, Status.SUCCESS, true),
                arrayOf(Status.LOADING, Status.ERROR, true),
                arrayOf(Status.SUCCESS, Status.SUCCESS, false),
                arrayOf(Status.SUCCESS, Status.ERROR, false),
                arrayOf(Status.ERROR, Status.LOADING, true),
                arrayOf(Status.ERROR, Status.SUCCESS, false),
                arrayOf(Status.ERROR, Status.ERROR, false),

            )
    }
}