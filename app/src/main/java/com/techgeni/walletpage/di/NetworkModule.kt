package com.techgeni.walletpage.di
import com.techgeni.walletpage.data.build.ktorClient.KtorApiClient
import com.techgeni.walletpage.data.build.ktorClient.providerKtorClient
import org.koin.dsl.module

object NetworkModule {

    val networkModule  = module {

        single { providerKtorClient() }
        single { KtorApiClient(get()) }

    }
}