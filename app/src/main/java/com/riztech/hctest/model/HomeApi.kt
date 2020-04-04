package com.riztech.hctest.model

import io.reactivex.Single
import retrofit2.http.GET

interface HomeApi {

    @GET("home")
    fun getHome(): Single<HomeResponse>
}