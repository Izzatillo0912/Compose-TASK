package com.techgeni.walletpage.data.remote.getWallet

import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.domain.getWallet.WalletRepository
import com.techgeni.walletpage.utils.Error
import com.techgeni.walletpage.utils.RemoteResult

class WalletRepositoryImpl(private val apiClient: KtorApiClient) : WalletRepository {
    override suspend fun getWallet(): RemoteResult<WalletResponseModel, Error> {
        return apiClient.get("wallet")
    }
}