package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.google.gson.Gson

class CreateArticleViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    fun createNewArticle(article: Article) {
        val gson = Gson()
        val articleJson: String = gson.toJson(article)
        val createdArticle: LiveData<Article> = liveData {
            commsRepository.createArticle(articleJson)?.also { emit(it) }
        }
    }

}