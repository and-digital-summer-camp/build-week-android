package com.and.newton.comms.domain.data

import com.google.gson.annotations.SerializedName

data class User constructor(
    val id: Int?,

    @field:SerializedName("first_name")
    val firstName: String?,

    @field:SerializedName("last_name")
    val lastNAme: String?,

    @field:SerializedName("email_address")
    val email:String?
)