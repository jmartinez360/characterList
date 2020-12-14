package com.android.data.repository

import com.android.data.model.DataResult

open class BaseRepository {

    protected fun <T: Any, R> mapResultOrThrow(
        result: DataResult<T>,
        mapFunction: (data: T) -> R
    ): R {
        return when(result) {
            is DataResult.Success -> mapFunction.invoke(result.data)
            is DataResult.Error -> throw result.exception
        }
    }
}