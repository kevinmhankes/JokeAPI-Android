
/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.kevinmhankes.jokeapi.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.kevinmhankes.jokeapi.api.ApiResponse
import retrofit2.Response

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