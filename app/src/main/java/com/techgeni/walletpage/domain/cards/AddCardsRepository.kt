package com.techgeni.walletpage.domain.cards

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

interface AddCardsRepository {

    suspend fun addNewCard(cardNumber : String, expireDate : String) : RemoteResult<MyCardModel, String>
}