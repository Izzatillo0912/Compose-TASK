package com.techgeni.walletpage.domain.getWallet

import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel
import com.techgeni.walletpage.utils.Error
import com.techgeni.walletpage.utils.RemoteResult

interface WalletRepository {
    suspend fun getWallet(): RemoteResult<WalletResponseModel, Error>
}