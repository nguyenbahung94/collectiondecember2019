package com.example.collectiondecember2019

import android.app.Application
import com.example.collectiondecember2019.first_ex_koin.di.module.movieModule
import com.example.collectiondecember2019.first_ex_koin.di.module.picassoModule
import com.example.collectiondecember2019.first_ex_koin.di.module.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyCustomApplication : Application() {
    companion object {
        private var instance: MyCustomApplication? = null

        fun applicationContext(): MyCustomApplication {
            return instance as MyCustomApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyCustomApplication)
            modules(listOf(retrofitModule, picassoModule, movieModule))
        }
    }
}