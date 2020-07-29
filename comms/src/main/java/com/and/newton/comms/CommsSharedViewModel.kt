package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.common.viewmodel.UserViewModel
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.domain.data.User
import com.google.gson.Gson

class CommsSharedViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    private var _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle


    val user: LiveData<User> = liveData {
        commsRepository.getUser("Bearer googletoken")?.also { emit(it)}
    }

    val articles: LiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { articleList : List<Article> ->
            val sortedArticleList = articleList.sortedWith(compareByDescending <Article> { it.highlighted }.thenByDescending { it.date }
            )
          emit(sortedArticleList)
        }

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

    fun postArticle(article: Article): LiveData<Article> = liveData {
        val articleJson: String = Gson().toJson(article)
        commsRepository.createArticle(articleJson)?.also { emit(it)}
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }
}