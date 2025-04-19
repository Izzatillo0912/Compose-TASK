package com.techgeni.walletpage.data.remote.addPromoCode

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.data.build.ktorClient.RemoteError
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.domain.addPromoCode.AddPromoCodeRepository

class AddPromoCodeRepositoryImpl(private val ktorApiClient: KtorApiClient) : AddPromoCodeRepository {

    override suspend fun addPromoCode(promoCode: String): RemoteResult<String, String> {
        return when (val response = ktorApiClient.post<AddPromoCodeResponseModel>(
            route = "promocode",
            body = AddPromoCodeModel(
                code = promoCode
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

            is RemoteResult.Success -> RemoteResult.Success(response.data.message)
        }
    }

}