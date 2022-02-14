package com.example.tokenizedretrofit.models

import okhttp3.ResponseBody

sealed class ResourceHttp<out T> {
    data class Success<out T>(val value: T) : ResourceHttp<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResourceHttp<Nothing>()

    object Loading : ResourceHttp<Nothing>()
}