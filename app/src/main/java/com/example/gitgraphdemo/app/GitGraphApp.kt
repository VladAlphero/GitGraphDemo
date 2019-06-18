package com.example.gitgraphdemo.app

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.example.gitgraphdemo.BuildConfig
import com.example.gitgraphdemo.repository.GitDataRepository
import com.example.gitgraphdemo.repository.GitDataRepositoryImpl
import com.example.gitgraphdemo.ui.MainActivityViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class GitGraphApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@GitGraphApp)
            // declare modules to use
            modules(appModule)
        }
    }
}

val appModule = module {

    single { getApolloClient() }

    // single instance of GitDataRepository
    single<GitDataRepository> { GitDataRepositoryImpl(get()) }

    // MyViewModel ViewModel
    viewModel { MainActivityViewModel(get()) }
}

fun getApolloClient(): ApolloClient {
    val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method(),
                original.body())
            builder.addHeader("Authorization"
                , "Bearer " + BuildConfig.AUTH_TOKEN)
            chain.proceed(builder.build())
        }
        .build()
    return ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(okHttpClient)
        .build()
}