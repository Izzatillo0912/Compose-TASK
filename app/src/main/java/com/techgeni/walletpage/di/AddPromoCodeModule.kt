package com.techgeni.walletpage.di

import com.techgeni.walletpage.data.remote.addPromoCode.AddPromoCodeRepositoryImpl
import com.techgeni.walletpage.data.remote.getWallet.WalletRepositoryImpl
import com.techgeni.walletpage.domain.addPromoCode.AddPromoCodeRepository
import com.techgeni.walletpage.domain.addPromoCode.AddPromoCodeUseCase
import com.techgeni.walletpage.domain.getWallet.WalletRepository
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.presentation.bottomSheets.addPromoCode.AddPromoCodeViewModel
import com.techgeni.walletpage.presentation.screens.wallet.viewModels.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object AddPromoCodeModule {
    val addPromoCodeModule = module {
        single<AddPromoCodeRepository> { AddPromoCodeRepositoryImpl(get()) }
        single { AddPromoCodeUseCase(get()) }
        viewModel { AddPromoCodeViewModel(get()) }
    }
}