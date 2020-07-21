package com.and.newton.comms.domain.data

import com.google.gson.annotations.SerializedName

data class Recipe constructor(

    val id: Int?,

    val title: String?,

    @field:SerializedName("readyInMinutes")
    val timeToPrepare: Int?,

    val servings: Int?,

    val sourceUrl: String?,

    @field:SerializedName("image")
    val imageUrl: String?
) {
    constructor(id: Int?, title: String?) : this(id, title, null, null, null, null)
    
}