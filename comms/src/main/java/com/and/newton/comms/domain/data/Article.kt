package com.and.newton.comms.domain.data

import java.io.Serializable

data class Article constructor(
    val id: Int?,

    val title: String?,

    val content: String?
) : Serializable