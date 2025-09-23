package com.veryshinnam.myapp.core.network

// api 기본 응답
data class BaseResponse<T>(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: T?
)