package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.domain.data.User

class CommsSharedViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    val user: LiveData<User> = liveData {
        commsRepository.getUser("Bearer googletoken")?.also { emit(it)}
    }

    val articles: LiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { emit(it)}
    }

    val article: LiveData<Article> = liveData {
        commsRepository.getArticle(1)?.also { emit(it)}
    }

    val highLightedArticles: LiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { emit(it)}
    }

    val categories: LiveData<List<Category>> = liveData {
        commsRepository.getCategories()?.also { emit(it)}
    }
}