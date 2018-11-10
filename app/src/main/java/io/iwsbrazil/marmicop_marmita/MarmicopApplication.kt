package io.iwsbrazil.marmicop_marmita

import android.app.Application
import io.iwsbrazil.marmicop_marmita.di.appModule
import org.koin.android.ext.android.startKoin

class MarmicopApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }
}