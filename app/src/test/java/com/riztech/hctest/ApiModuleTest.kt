package com.riztech.hctest

import com.riztech.hctest.di.ApiModule
import com.riztech.hctest.model.HomeApiService

class ApiModuleTest(val mockService: HomeApiService): ApiModule() {
    override fun provideHomeApiService(): HomeApiService {
        return mockService
    }
}