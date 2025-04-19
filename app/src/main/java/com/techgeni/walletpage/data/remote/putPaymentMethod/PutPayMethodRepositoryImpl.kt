package com.techgeni.walletpage.data.remote.putPaymentMethod

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.data.build.ktorClient.RemoteError
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel
import com.techgeni.walletpage.domain.putPayMethod.PutPayMethodRepository

class PutPayMethodRepositoryImpl(private val ktorApiClient: KtorApiClient) : PutPayMethodRepository {

    override suspend fun putPayMethod(method: String, cardId: Int): RemoteResult<WalletResponseModel, String> {

        return when (val response = ktorApiClient.put<WalletResponseModel>(
            route = "wallet/method",
            body = PutPayMethodModel(
                activeMethod = method,
                activeCardId = cardId
            )
        )) {
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