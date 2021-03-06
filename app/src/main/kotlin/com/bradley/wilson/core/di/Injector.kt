package com.bradley.wilson.core.di

import android.content.Context
import com.bradley.wilson.currency.di.currencyFeedModule
import com.bradley.wilson.core.database.di.storageModule
import com.bradley.wilson.core.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object Injector {

    private val coreModules = listOf(
        networkModule,
        storageModule
    )

    private val featureModules = listOf(
        currencyFeedModule
    )

    @JvmStatic
    fun start(context: Context) {
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    coreModules,
                    featureModules
                ).flatten()
            )
        }

    }
}

