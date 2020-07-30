package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category

class CommsSharedViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    val articles: LiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { articleList : List<Article> ->
            val sortedArticleList = articleList.sortedWith(compareByDescending <Article> { it.highlighted }.thenByDescending { it.date }
            )
          emit(sortedArticleList)
        }
    }

    fun fetchArticles(): LiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { articleList : List<Article> ->
            val sortedArticleList = articleList.sortedWith(compareByDescending <Article> { it.highlighted }.thenByDescending { it.date }
            )
            emit(sortedArticleList)
        }
    }

    val article: LiveData<Article> = liveData {
        commsRepository.getArticle(6)?.also { emit(it)}
    }

    fun fetchCategories(): LiveData<List<Category>> = liveData {
        commsRepository.getCategories()?.also { emit(it)}
    }

    val categories: LiveData<List<Category>> = liveData {
        commsRepository.getCategories()?.also { emit(it)}
    }

    fun postArticle(article: Article): LiveData<Boolean> = liveData {
        commsRepository.createArticle(article)?.also { emit(true)}
    }

    fun updateArticle(id: Int, article: Article): LiveData<Boolean> = liveData {
        commsRepository.updateArticle(id, article).also { emit(true)}
    }

    fun createCategory(category: Category): LiveData<Boolean> = liveData {
        commsRepository.createCategory(category)?.also { emit(true)}
    }

}
