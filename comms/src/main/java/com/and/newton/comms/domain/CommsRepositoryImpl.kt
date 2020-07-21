package com.and.newton.comms.domain

import android.content.Context
import com.and.newton.comms.domain.data.Recipe
import com.and.newton.comms.network.CommsAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CommsRepositoryImpl @Inject constructor() : CommsRepository {
//private val commsAPI: CommsAPI
    override suspend fun getRecipe(query: String): List<Recipe>? {
        val apiHost = "context.resources.getString(R.string.api_host)"
        val apiKey = ""
        //commsAPI.getRecipe(query, apiHost, apiKey)?.results
        return listOf<Recipe>()
    }

}