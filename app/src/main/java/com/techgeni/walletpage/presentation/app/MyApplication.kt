package com.techgeni.walletpage.presentation.app

import android.app.Application
import com.techgeni.walletpage.di.AddPromoCodeModule.addPromoCodeModule
import com.techgeni.walletpage.di.CardsModule.cardsModule
import com.techgeni.walletpage.di.NetworkModule.networkModule
import com.techgeni.walletpage.di.WalletModule.walletModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule,
                walletModule,
                cardsModule,
                addPromoCodeModule
            )
        }
    }

}