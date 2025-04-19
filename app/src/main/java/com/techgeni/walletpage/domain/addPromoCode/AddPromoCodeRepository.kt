package com.techgeni.walletpage.domain.addPromoCode

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

interface AddPromoCodeRepository {
    suspend fun addPromoCode(promoCode: String) : RemoteResult<String, String>
}