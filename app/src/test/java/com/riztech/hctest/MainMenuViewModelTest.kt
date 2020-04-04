package com.riztech.hctest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riztech.hctest.di.DaggerViewModelComponent
import com.riztech.hctest.model.HomeApiService
import com.riztech.hctest.model.HomeModel
import com.riztech.hctest.model.HomeResponse
import com.riztech.hctest.model.ItemsModel
import com.riztech.hctest.viewModel.MainMenuViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class MainMenuViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var homeApiService: HomeApiService

    val application = Mockito.mock(Application::class.java)

    var mainMenuViewModel = MainMenuViewModel(application, true)

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.create().inject(mainMenuViewModel)
    }


    @Before
    fun setupRxScheduler(){
        val immedite = object : Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler-> immedite }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler-> immedite }
    }

    @Test
    fun getHomeDataSuccess(){

        val item = ItemsModel("test", "test","test", "test", "test")
        val homeModel = HomeModel("product", "test", arrayListOf(item,item,item,item,item,item))
        val homeResponse = HomeResponse(arrayListOf(homeModel))

        val testSingle = Single.just(homeResponse)

        Mockito.`when`(homeApiService.getHome()).thenReturn(testSingle)


        mainMenuViewModel.refresh()

        Assert.assertEquals(6, mainMenuViewModel.listData.value?.size)
        Assert.assertEquals(false, mainMenuViewModel.loading.value)
        Assert.assertEquals(false, mainMenuViewModel.loadError.value)

    }

}