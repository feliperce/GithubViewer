package com.example.githubviewer.di

import com.example.githubviewer.BuildConfig
import com.example.githubviewer.data.remote.service.GithubService
import com.example.githubviewer.data.repository.RepRepository
import com.example.githubviewer.ui.rep.viewmodel.RepSearchViewModel
import com.example.githubviewer.ui.reppr.viewmodel.RepPrViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun githubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    single { retrofit() }
    single { githubService(get()) }
}

val respositoryModule = module {
    factory { RepRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { RepSearchViewModel(get()) }
    viewModel { RepPrViewModel(get()) }
}