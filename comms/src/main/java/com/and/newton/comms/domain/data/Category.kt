package com.and.newton.comms.domain.data

import com.google.gson.annotations.SerializedName

data class Category constructor(
    @field:SerializedName("categoryID")
    val id: Int?,

    @field:SerializedName("categoryName")
    val name: String?
)