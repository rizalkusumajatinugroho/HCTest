package com.riztech.hctest.model

import com.google.gson.annotations.SerializedName

data class HomeModel (
    val section: String?,
    @SerializedName("section_title")
    val sectionTitle: String?,
    val items: List<ItemsModel>?
)