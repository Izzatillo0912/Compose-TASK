package com.techgeni.walletpage.domain.cards

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

interface AllCardsRepository {

    suspend fun getAllCards() : RemoteResult<ArrayList<MyCardModel>, String>

}