package com.riztech.hctest.model

import com.riztech.hctest.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class HomeApiService {

    @Inject
    lateinit var api: HomeApi

    init {
        DaggerApiComponent.create().inject(this)
    }


    fun getHome(): Single<HomeResponse>{
        return api.getHome()
    }

}