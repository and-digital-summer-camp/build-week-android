package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Recipe
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

interface CommsRepository {
    suspend fun getRecipe(query: String): List<Recipe>?
}

