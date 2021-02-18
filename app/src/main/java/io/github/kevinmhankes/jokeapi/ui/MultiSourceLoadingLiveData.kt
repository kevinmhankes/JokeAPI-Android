package io.github.kevinmhankes.jokeapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.github.kevinmhankes.jokeapi.api.util.Resource
import io.github.kevinmhankes.jokeapi.api.util.Status

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * A [MediatorLiveData] object that takes in a list of [LiveData] [Resource] sources and emits
 * a boolean loading status value. The loading value is true if the source status
 * are [Status.LOADING]. The new result value is only emitted if it differs from the current value.
 *
 * When using this class, you should not manually [setValue] or [addSource].
 *
 * @param sources the list of [Resource] to listen to for loading status.
 */
class MultiSourceLoadingLiveData(
    private val sources: List<LiveData<out Resource<Any?>>> = emptyList(),
) :
    MediatorLiveData<Boolean>() {
    init {
        sources.forEach { source ->
            addSource(source) {
                value = anySourceCurrentlyLoading()
            }
        }
    }

    override fun setValue(value: Boolean?) {
        if (value != this.value) {
            super.setValue(value)
        }
    }

    /**
     * Are any of the [sources] currently [Status.LOADING]
     */
    private fun anySourceCurrentlyLoading() =
        sources.any { it.value?.status == Status.LOADING }
}