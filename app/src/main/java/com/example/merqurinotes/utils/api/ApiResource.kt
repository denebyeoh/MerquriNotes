package com.example.merqurinotes.utils.api

sealed class ApiResource<out T: Any> {
    data class Success<out T: Any>(val data: T): ApiResource<T>()
    data class Error(val message: String = "", val code: String = ""): ApiResource<Nothing>()
    object Loading: ApiResource<Nothing>()
}