package com.riztech.hctest.di

import com.riztech.hctest.viewModel.MainMenuViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: MainMenuViewModel)
}