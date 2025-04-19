package com.techgeni.walletpage.di

import com.techgeni.walletpage.data.remote.addCard.AddCardRepositoryImpl
import com.techgeni.walletpage.data.remote.getAllCards.AllAllCardsRepositoryImpl
import com.techgeni.walletpage.domain.cards.AddCardsRepository
import com.techgeni.walletpage.domain.cards.AllCardsRepository
import com.techgeni.walletpage.domain.cards.CardsUseCase
import com.techgeni.walletpage.presentation.screens.addCard.AddCardViewModel
import com.techgeni.walletpage.presentation.screens.wallet.viewModels.CardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object CardsModule {
    val cardsModule = module {
        single<AllCardsRepository> { AllAllCardsRepositoryImpl(get()) }
        single<AddCardsRepository> { AddCardRepositoryImpl( get() )}
        single { CardsUseCase(get(), get()) }
        viewModel { CardsViewModel(get()) }
        viewModel { AddCardViewModel(get()) }
    }
}