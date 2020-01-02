package com.example.collectiondecember2019

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.collectiondecember2019.converter.TokenExpired
import com.example.collectiondecember2019.first_ex_koin.di.module.movieModule
import com.example.collectiondecember2019.first_ex_koin.di.module.picassoModule
import com.example.collectiondecember2019.first_ex_koin.di.module.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kotlin.system.exitProcess

class MyCustomApplication : Application() {
    private val activityLifecycleMgr = ActivityLifecycleCallBack()

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

        val oldHandler: Thread.UncaughtExceptionHandler? =
            Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            when {
                exception.cause is TokenExpired -> {
                    activityLifecycleMgr.foreground?.let {
                        //do something
                    }
                }
                oldHandler != null -> {
                    oldHandler.uncaughtException(thread, exception)
                }
                else -> {
                    exception.printStackTrace()
                    exitProcess(0)
                }
            }
        }
    }
}