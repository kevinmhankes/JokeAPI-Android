package io.github.kevinmhankes.jokeapi.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.kevinmhankes.jokeapi.api.ApiResponse
import retrofit2.Response

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */
/**
 * Testing convenience class for wrapping api network return data into success objects within [LiveData]
 */
object ApiUtil {

    /**
     * Convenience method to call [ApiUtil.createCall] with a [Response.success] object carrying the given [data]
     */
    fun <T : Any> successCall(data : T) = createCall<T>(Response.success(data))

    /**
     * Wrap the given [Response] in an [ApiResponse] in a [LiveData]
     */
    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse.create(response)
    } as LiveData<ApiResponse<T>>
}