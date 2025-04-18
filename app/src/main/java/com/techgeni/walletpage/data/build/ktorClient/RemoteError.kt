package com.techgeni.walletpage.data.build.ktorClient

sealed class RemoteError(val message: String) {
    class RequestError(code: Int = 0) : RemoteError(code.toString())
    class ServerError(message: String) : RemoteError(message)
    class ConnectionError : RemoteError("Time out error")
    class SerializationError : RemoteError("Serialization error")
    class Unknown(message: String) : RemoteError(message)
}