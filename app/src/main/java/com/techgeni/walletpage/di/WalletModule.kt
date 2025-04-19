package com.techgeni.walletpage.di

import com.techgeni.walletpage.data.remote.getWallet.WalletRepositoryImpl
import com.techgeni.walletpage.data.remote.putPaymentMethod.PutPayMethodRepositoryImpl
import com.techgeni.walletpage.domain.getWallet.WalletRepository
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.domain.putPayMethod.PutPayMethodRepository
import com.techgeni.walletpage.domain.putPayMethod.PutPayMethodUseCase
import com.techgeni.walletpage.presentation.screens.wallet.viewModels.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object WalletModule {
    val walletModule = module {
        single<WalletRepository> { WalletRepositoryImpl(get()) }
        single<PutPayMethodRepository>{ PutPayMethodRepositoryImpl(get()) }
        single { WalletUseCase(get()) }
        single { PutPayMethodUseCase(get()) }
        viewModel { WalletViewModel(get(), get()) }
    }
}