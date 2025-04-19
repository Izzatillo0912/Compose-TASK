package com.techgeni.walletpage.domain.addPromoCode

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

class AddPromoCodeUseCase(private val addPromoCodeRepository: AddPromoCodeRepository) {

    suspend fun addPromoCode(promoCode: String) : RemoteResult<String, String> {
        return addPromoCodeRepository.addPromoCode(promoCode)
    }

}