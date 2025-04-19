package com.techgeni.walletpage.domain.putPayMethod

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel

class PutPayMethodUseCase(private val putPayMethodRepository: PutPayMethodRepository) {

    suspend fun putPayMethod(activeMethod: String, activeCardId: Int): RemoteResult<WalletResponseModel, String> {
        return putPayMethodRepository.putPayMethod(activeMethod, activeCardId)
    }

}