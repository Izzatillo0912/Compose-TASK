package com.techgeni.walletpage.data.remote.getWallet

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.domain.getWallet.WalletRepository
import com.techgeni.walletpage.data.build.ktorClient.RemoteError
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

class WalletRepositoryImpl(private val apiClient: KtorApiClient) : WalletRepository {
    override suspend fun getWallet(): RemoteResult<WalletResponseModel, String> {

        return when (val response = apiClient.get<WalletResponseModel>("wallet")) {
            is RemoteResult.Error -> {
                val message = when (val error = response.error) {
                    is RemoteError.RequestError -> "Request Error: ${error.message}"
                    is RemoteError.ServerError -> "Server Error: ${error.message}"
                    is RemoteError.ConnectionError -> "Connection Error"
                    is RemoteError.SerializationError -> "Serialization Error"
                    is RemoteError.Unknown -> "Unknown Error: ${error.message}"
                }
                RemoteResult.Error(message)
            }

            is RemoteResult.Success -> RemoteResult.Success(response.data)
        }
    }
}
