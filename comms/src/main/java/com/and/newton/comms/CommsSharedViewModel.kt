package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category

class CommsSharedViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {


    var categoryList: MutableList<String> = mutableListOf("All Categories")


    var articles: MutableLiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { articleList : List<Article> ->
            val sortedArticleList = articleList.sortedWith(compareByDescending <Article> { it.highlighted }.thenByDescending { it.date }
            )
            emit(sortedArticleList)
        }
    } as MutableLiveData<List<Article>>


    fun fetchArticles(): MutableLiveData<List<Article>> = liveData {
        commsRepository.getArticles()?.also { articleList : List<Article> ->
            val sortedArticleList = articleList.sortedWith(compareByDescending <Article> { it.highlighted }.thenByDescending { it.date }
            )
            emit(sortedArticleList)
        }
    } as MutableLiveData<List<Article>>

    var article: MutableLiveData<Article> = liveData {
        commsRepository.getArticle(6)?.also { emit(it)}
    } as MutableLiveData<Article>

    fun fetchCategories(): LiveData<List<String>> = liveData {
        commsRepository.getCategories()?.also { emit(getCategoryNames(it))}
    }

    val categories: LiveData<List<String>> = liveData {
        commsRepository.getCategories()?.also {
            emit(getCategoryNames(it))}
    }

    private fun getCategoryNames(categories: List<Category>) : List<String>{
        categoryList = categories.map {
            it.name?:"N/A"
        } as MutableList<String>

        categoryList.add(0,"All Categories")

        return categoryList
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
