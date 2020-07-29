package com.and.newton.comms.domain.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Article constructor(
    @field:SerializedName("articleID")
    val id: Int?,

    val title: String?,

    val content: String?,

    @field:SerializedName("picture")
    val imagePath:String?,

    @field:SerializedName("dateCreated")
    val date:Date?,

    @field:SerializedName("dateLastUpdated")
    val dateUpdated:Date?,

    val highlighted:Boolean?,

    @field:SerializedName("articleCategories")
    val categories:List<CategoryHolder>?
) : Serializable