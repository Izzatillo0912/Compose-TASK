package com.techgeni.walletpage.data.remote.addCard

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.data.build.ktorClient.RemoteError
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.data.remote.getAllCards.GetCardModel
import com.techgeni.walletpage.domain.cards.AddCardsRepository
import com.techgeni.walletpage.domain.cards.MyCardModel

class AddCardRepositoryImpl(private val apiClient: KtorApiClient) : AddCardsRepository {

    override suspend fun addNewCard(cardNumber: String, expireDate: String): RemoteResult<MyCardModel, String> {
        return when (val response = apiClient.post<GetCardModel>(
            route = "cards",
            body = AddCardModel(
                number = cardNumber,
                expireDate = expireDate
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

            is RemoteResult.Success -> RemoteResult.Success(
                MyCardModel(
                    id = response.data.id,
                    number = response.data.number
                )
            )
        }
    }
}