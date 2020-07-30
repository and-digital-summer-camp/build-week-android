package com.and.newton.comms.domain.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Article constructor(
    @field:SerializedName("articleID")
    val id: Int?,

    var title: String?,

    var content: String?,

    @field:SerializedName("picture")
    var imagePath:String?,

    @field:SerializedName("dateCreated")
    val date:Date?,

    @field:SerializedName("dateLastUpdated")
    val dateUpdated:Date?,

    var highlighted:Boolean?,

    @field:SerializedName("articleCategories")
    var categories:List<CategoryHolder>?
) : Serializable