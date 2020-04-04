package com.riztech.hctest.di

import com.riztech.hctest.model.HomeApi
import com.riztech.hctest.model.HomeApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {
    private val BASE_URL = "https://private-a8e48-hcidtest.apiary-mock.com/"

    @Provides
    fun provideHomeApi(): HomeApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }

    @Provides
    open fun provideHomeApiService(): HomeApiService{
        return HomeApiService()
    }
}