package com.riztech.hctest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.riztech.hctest.di.DaggerViewModelComponent
import com.riztech.hctest.model.ArticleItem
import com.riztech.hctest.model.HomeApiService
import com.riztech.hctest.model.HomeResponse
import com.riztech.hctest.model.ProductItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainMenuViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application, test: Boolean = true): this(application){
        injected = true
    }
    val listData by lazy { MutableLiveData<ArrayList<Any>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: HomeApiService

    private var injected = false;

    fun inject() {
        if (!injected)
        DaggerViewModelComponent.create().inject(this)
    }

    fun refresh(){
        inject()
        loading.value = true
        getData()
    }

    private fun getData(){
       disposable.add(
           apiService.getHome()
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith( object :DisposableSingleObserver<HomeResponse>(){
                   override fun onSuccess(homeResponse: HomeResponse) {
                       if (homeResponse != null && homeResponse.data != null){
                           loading.value = false
                           loadError.value = false

                           var listProductItem : ArrayList<ProductItem> = arrayListOf()
                           var listArticle: ArrayList<ArticleItem> = arrayListOf()
                           var articleTitle = ""
                           for(valueData in homeResponse.data){
                               if (valueData.section.equals("products") && valueData.items != null){
                                   for (productItem in valueData.items){
                                       var productData = ProductItem(productItem.productName, productItem.productImage, productItem.link)

                                       listProductItem.add(productData)
                                   }
                               }else if (valueData.section.equals("articles") && valueData.items != null){
                                  valueData.sectionTitle?.let {
                                      articleTitle = valueData.sectionTitle
                                  }

                                   for (articleItem in valueData.items){
                                       var articleData = ArticleItem(articleItem.articleTitle, articleItem.articleImage, articleItem.link)
                                       listArticle.add(articleData)
                                   }
                               }
                           }

                           var list = arrayListOf<Any>(listProductItem, articleTitle)
                           for (articleItem in listArticle){
                               list.add(articleItem)
                           }
                           listData.value = list
                       }
                   }

                   override fun onError(e: Throwable) {
                        e.printStackTrace()
                       loading.value = false
                       loadError.value = true
                   }

               })
       )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}