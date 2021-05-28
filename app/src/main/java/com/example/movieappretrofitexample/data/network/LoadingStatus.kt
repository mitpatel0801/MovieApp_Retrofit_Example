package com.example.movieappretrofitexample.data.network

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

enum class ErrorCode {
    NO_DATA,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}


data class LoadingStatus(val status: Status, val errorCode: ErrorCode?, val message: String?) {

    companion object {
        fun loading() =
            LoadingStatus(
                Status.LOADING,
                null,
                null
            )

        fun error(errorCode: ErrorCode, message: String? = null) =
            LoadingStatus(
                Status.ERROR,
                errorCode,
                message
            )

        fun success(errorCode: ErrorCode? = null, message: String? = null) =
            LoadingStatus(
                Status.SUCCESS,
                errorCode,
                message
            )
    }
}