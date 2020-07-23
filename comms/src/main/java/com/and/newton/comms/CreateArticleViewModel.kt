package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.google.gson.Gson

class CreateArticleViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    private val _newArticle = MutableLiveData<Article>()
    val newArticle: LiveData<Article>
        get() = _newArticle

    fun createNewArticle(article: Article) : LiveData<Article> = liveData {
        val gson = Gson()
        val articleJson: String = gson.toJson(article)
        _newArticle.value = commsRepository.createArticle(articleJson)
    }

}