package com.and.newton.comms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.network.ApiData
import com.and.newton.comms.network.DataStatus

class CommsSharedViewModel @ViewModelInject constructor(private val commsRepository: CommsRepository) :
    ViewModel() {

    private var _createArticle = MutableLiveData<ApiData<Article>>()

    val createArticle: LiveData<ApiData<Article>>
        get() = _createArticle

    var categoryList: MutableList<String> = mutableListOf("All Categories")

    val articles: MutableLiveData<ApiData<List<Article>>> = liveData {
        commsRepository.getArticles().also { apiData: ApiData<List<Article>> ->
            val articleList = apiData.data
            val sortedArticleList =
                articleList?.sortedWith(compareByDescending<Article> { it.highlighted }.thenByDescending { it.date })
            apiData.data = sortedArticleList
            emit(apiData)
        }
    } as MutableLiveData<ApiData<List<Article>>>

    val categories: MutableLiveData<List<String>> = liveData {
        commsRepository.getCategories().also {
            if (it.status == DataStatus.SUCCESS) {
                if (it.data != null) {
                    emit(getCategoryNames(it.data!!))
                }
            }
        }
    } as MutableLiveData<List<String>>

    fun postArticle(article: Article): MutableLiveData<ApiData<Article>> = liveData {
        commsRepository.createArticle(article).also { emit(it) }
    } as MutableLiveData<ApiData<Article>>

    fun updateArticle(id: Int, article: Article): MutableLiveData<ApiData<Article>> = liveData {
        commsRepository.updateArticle(id, article).also { emit(it) }
    } as MutableLiveData<ApiData<Article>>

    fun createCategory(category: Category): MutableLiveData<ApiData<Category>> = liveData {
        commsRepository.createCategory(category).also { emit(it) }
    } as MutableLiveData<ApiData<Category>>

    private fun getCategoryNames(categories: List<Category>): List<String> {
        categoryList = categories.map {
            it.name ?: "N/A"
        } as MutableList<String>

        categoryList.add(0, "All Categories")

        return categoryList
    }

}
