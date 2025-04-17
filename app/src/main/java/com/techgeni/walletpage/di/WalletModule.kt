package com.techgeni.walletpage.di

import com.techgeni.walletpage.data.remote.getWallet.WalletRepositoryImpl
import com.techgeni.walletpage.domain.getWallet.WalletRepository
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.presentation.screens.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object WalletModule {
    val walletModule = module {
        single<WalletRepository> { WalletRepositoryImpl(get()) }
        single { WalletUseCase(get()) }
        viewModel { WalletViewModel(get()) }
    }
}