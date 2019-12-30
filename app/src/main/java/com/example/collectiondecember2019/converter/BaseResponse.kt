package com.example.collectiondecember2019.converter

class BaseResponse<T> {
    val code: String? = null
    val message: String? = null
    val value: T? = null
    val isSuccess: Boolean = false

    fun valid() = code != null || message != null || value != null || isSuccess
}