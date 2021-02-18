package io.github.kevinmhankes.jokeapi.api.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.github.kevinmhankes.jokeapi.AppExecutors
import io.github.kevinmhankes.jokeapi.api.ApiEmptyResponse
import io.github.kevinmhankes.jokeapi.api.ApiErrorResponse
import io.github.kevinmhankes.jokeapi.api.ApiResponse
import io.github.kevinmhankes.jokeapi.api.ApiSuccessResponse
import timber.log.Timber

/**
 * @author Kevin.
 * Created/Modified on February 14, 2021
 */

/**
 * An extension of [NetworkBoundResource] for dealing with the different data lists from the api.
 *
 * Common actions involved in fetching any of the JokeResponse types are overridden in this object.
 * - For [saveCallResult], the joke list is saved to their correct table.
 * - The [shouldFetch] is implemented here, as the common joke endpoint should have a common cache policy.
 * - The [onFetchFailed] is implemented to clear the cache on failed fetch.
 * - The [createCall] is implement to always call the joke endpoint.
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread
constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // request new live data, otherwise we get immediately cached value
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk what was previously there
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }

    }

    /**
     * Optional method to override for unique handling of a failed network request
     */
    protected open fun onFetchFailed() {
        Timber.d("A network request failed.")
    }

    /**
     * The [LiveData] object to listen on for this network backed data request.
     */
    fun asLiveData() = result as LiveData<Resource<ResultType>>

    /**
     * Process the response object from the network into an object to be passed to the [saveCallResult]method
     * If not overridden, the default is to return [response].body
     */
    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    /**
     * Save the result of the network api call into the database
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * Called to determine if a fresh value should be fetched from the network via the [createCall] method
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * Load the cached value from the database
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * Call the network service to get fresh data
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}