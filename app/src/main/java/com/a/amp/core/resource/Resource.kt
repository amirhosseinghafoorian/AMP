package com.a.amp.core.resource

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int = 0
) {
    companion object {
        fun <T> success(data: T?, code: Int): Resource<T> {
            return Resource(Status.SUCCESS, data, null, code)
        }

        fun <T> error(msg: String, data: T?, code: Int): Resource<T> {
            return Resource(Status.ERROR, data, msg, code)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}