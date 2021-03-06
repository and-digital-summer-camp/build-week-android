package com.and.newton.common.domain.data

import com.google.gson.annotations.SerializedName

data class User constructor(
    val id: Int?,

    @field:SerializedName("first_name")
    val firstName: String?,

    @field:SerializedName("last_name")
    val lastName: String?,

    @field:SerializedName("email_address")
    val email:String?,

    @field:SerializedName("access_level")
    val accessLevel:String,

    @field:SerializedName("valid")
    val valid:Boolean
)