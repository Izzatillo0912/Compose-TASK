package com.techgeni.walletpage.utils

sealed class RemoteResult<out D, out E: Error> {

    data object Init : RemoteResult<Nothing, Nothing>()

    data object Loading : RemoteResult<Nothing, Nothing>()

    data class Success<D>(val data: D): RemoteResult<D, Nothing>()

    data class Error<E: com.techgeni.walletpage.utils.Error>(val error: E): RemoteResult<Nothing, E>()
}