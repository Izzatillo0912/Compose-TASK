package com.techgeni.walletpage.data.remote.getAllCards

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.data.build.ktorClient.RemoteError
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.domain.cards.AllCardsRepository
import com.techgeni.walletpage.domain.cards.MyCardModel

class AllAllCardsRepositoryImpl(private val apiClient: KtorApiClient) : AllCardsRepository {

    override suspend fun getAllCards(): RemoteResult<ArrayList<MyCardModel>, String> {
        return when (val response = apiClient.get<ArrayList<GetCardModel>>(route = "cards")) {
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

            is RemoteResult.Success -> {
                val cards = ArrayList<MyCardModel>()
                response.data.forEach {
                    cards.add(MyCardModel(id = it.id, number = it.number))
                }
                RemoteResult.Success(cards)
            }
        }
    }
}