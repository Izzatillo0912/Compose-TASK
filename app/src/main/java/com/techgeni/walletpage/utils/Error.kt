package com.techgeni.walletpage.utils

sealed interface Error {

    enum class Remote : Error {
        REQUEST_ERROR,
        SERVER_ERROR,
        NO_INTERNET_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN
    }
}

data class RemoteErrorWithCode(
    val error: Error,
    val code: Int = 0
) : Error