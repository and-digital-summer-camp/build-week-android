package com.and.newton.comms.domain.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class Article constructor(
    @field:SerializedName("articleID")
    val id: Int?,

    val title: String?,

    val content: String?,

    @field:SerializedName("picture")
    val imagePath:String?,

    val date:Date?,

    val highlighted:Boolean?,

    val categories:List<Category>?
)