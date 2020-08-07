package com.and.newton.comms.network


data class ApiData<T>(val status: DataStatus, var data: T?, val message: String?, val responseCode : Int?) {
    companion object {
        fun <T> success(data: T?): ApiData<T> {
            return ApiData(DataStatus.SUCCESS, data, null, null)
        }

        fun <T> error(responseCode : Int, msg: String, data: T?): ApiData<T> {
            return ApiData(DataStatus.ERROR, data, msg, responseCode)
        }
    }
}