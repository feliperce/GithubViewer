package com.example.githubviewer.application

import android.app.Application
import com.example.githubviewer.di.respositoryModule
import com.example.githubviewer.di.retrofitModule
import com.example.githubviewer.di.viewModelModule
import org.koin.core.context.startKoin

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(arrayListOf(
                retrofitModule,
                respositoryModule,
                viewModelModule
            ))
        }
    }
}