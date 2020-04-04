package com.riztech.hctest.model

import com.google.gson.annotations.SerializedName

data class ItemsModel (
    @SerializedName("article_title")
    val articleTitle: String?,
    @SerializedName("article_image")
    val articleImage: String?,
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("product_image")
    val productImage: String?,
    val link: String?
)