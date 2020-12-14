package com.android.data_api.data

import com.android.data_api.model.ApiResult
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import kotlin.random.Random

open class DataSource {

    open fun <T : Any> handleApiResponse(response: Response<T>): ApiResult<T> {
        return if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Error(IOException(response.message()))
        }
    }

    protected suspend fun addRandomDelay() {
        delay(getRandomTime())
    }

    private fun getRandomTime() =  Random.nextLong(1000, 2000)

}