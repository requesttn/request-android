package tn.request

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tn.request.network.BackendService
import tn.request.ui.LoginViewModel

class App : Application() {
    private val appModule = module {
        single {
            BackendService()
        }
    }

    private val viewModelsModule = module {
        factory { LoginViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, viewModelsModule)
        }

    }
}