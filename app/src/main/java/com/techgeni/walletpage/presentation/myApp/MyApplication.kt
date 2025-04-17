package com.techgeni.walletpage.presentation.myApp

import android.app.Application
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
                walletModule
            )
        }
    }

}