package com.techgeni.walletpage.domain.cards

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

class CardsUseCase(
    private val allCardsRepository: AllCardsRepository,
    private val addCardsRepository: AddCardsRepository
) {

    suspend fun getAllCards() : RemoteResult<ArrayList<MyCardModel>, String> {
        return allCardsRepository.getAllCards()
    }

    suspend fun addNewCard(cardNumber : String, expireDate : String) : RemoteResult<MyCardModel, String> {
        return addCardsRepository.addNewCard(cardNumber, expireDate)
    }

}