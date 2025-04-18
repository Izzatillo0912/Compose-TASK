package com.techgeni.walletpage.domain.getWallet

import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult

class WalletUseCase(private val walletRepository: WalletRepository) {
    suspend fun getWallet(): RemoteResult<WalletResponseModel, String> {
        return walletRepository.getWallet()
    }

}