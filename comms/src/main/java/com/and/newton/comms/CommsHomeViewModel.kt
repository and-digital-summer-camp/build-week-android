package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.domain.data.User
import com.google.gson.Gson
import timber.log.Timber

class CommsHomeViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
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

    val categories: LiveData<List<Category>> = liveData {
        commsRepository.getAllCategories().also { emit(it)}
    }

    fun postArticle(article: Article): LiveData<Article> = liveData {
        val articleJson: String = Gson().toJson(article)
        commsRepository.createArticle(articleJson)?.also { emit(it)}
    }

}