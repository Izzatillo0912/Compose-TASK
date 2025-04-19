package com.techgeni.walletpage.domain.putPayMethod

import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel

interface PutPayMethodRepository {

    suspend fun putPayMethod(method : String, cardId : Int) : RemoteResult<WalletResponseModel, String>

}