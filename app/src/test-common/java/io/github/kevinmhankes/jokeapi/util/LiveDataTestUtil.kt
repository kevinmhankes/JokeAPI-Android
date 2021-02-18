package io.github.kevinmhankes.jokeapi.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */
object LiveDataTestUtil {

    /**
     * Helper for getting the value of a [LiveData] object
     * within unit or instrumented tests.
     */
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)

        // To test timeouts, this needs to be greater than the 10
        // second default timeout of OkHttpClient
        latch.await(11, TimeUnit.SECONDS)

        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }
}