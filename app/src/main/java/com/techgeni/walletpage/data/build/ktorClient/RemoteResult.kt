package com.techgeni.walletpage.data.build.ktorClient

sealed class RemoteResult<out D, out E> {

    data class Success<D>(val data: D): RemoteResult<D, Nothing>()

    data class Error<E>(val error: E): RemoteResult<Nothing, E>()
}