package com.and.newton.comms.network

import retrofit2.Response

class ResponseHandler<T> {

    companion object {
        fun <T> handleResponse(response: Response<T>) : ApiData<T> {
            return if (response.isSuccessful) {
                ApiData.success(response.body())
            } else {
                ApiData.error(response.code(), response.message(), response.body())
            }
        }
    }

}