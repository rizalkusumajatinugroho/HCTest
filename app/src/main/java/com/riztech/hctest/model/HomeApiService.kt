package com.riztech.hctest.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeApiService {


    private val BASE_URL = "https://private-a8e48-hcidtest.apiary-mock.com/"


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(HomeApi::class.java)


    fun getHome(): Single<HomeResponse>{
        return api.getHome()
    }

}