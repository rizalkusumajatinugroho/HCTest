package com.riztech.hctest.di

import com.riztech.hctest.model.HomeApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: HomeApiService)
}