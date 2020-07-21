package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Recipe

class CommsHomeViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
            ViewModel() {

    //private val commsRepository: CommsRepository
        var categoryFilter = "Burger"

//    var recipeListData: List<Recipe> = listOf<Recipe>(
//        Recipe(1, "recipe1", 10, 5),
//        Recipe(2, "recipe2", 5, 2),
//        Recipe(3, "recipe3", 15, 4),
//        Recipe(4, "recipe4", 10, 3),
//        Recipe(5, "recipe5", 5, 2)
//    )

        val recipe: LiveData<List<Recipe>> = liveData {
//            commsRepository.getRecipe(categoryFilter)?.also { emit(it) }
        }
    }