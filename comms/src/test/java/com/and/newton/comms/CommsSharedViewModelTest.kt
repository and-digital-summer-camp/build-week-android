package com.and.newton.comms

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.network.ApiData
import com.and.newton.comms.network.DataStatus
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * A note to anyone who tries to pick up the unit tests for the other functions in this model
 * - The reason there isn't any tests is because the ViewModel function calls were not being picked up
 * by the ViewModel so the LiveData's weren't being updated and so the observers never saw a value change.
 * LiveData and Coroutines are relatively new at the current time so hopefully in the future this gets better support.
 */
class CommsSharedViewModelTest {

    @Mock
     var mockCommsRepository: CommsRepository = mock(CommsRepository::class.java)

    lateinit var commsViewModel: CommsSharedViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.commsViewModel = CommsSharedViewModel(this.mockCommsRepository)
    }

    @Test
    fun testGetArticlesSuccess() = runBlocking {
        val article = Article(0,"title1","content1",null,null,null, true,null)
        val apiResponse = ApiData.success(listOf(article))

        Mockito.`when`(mockCommsRepository.getArticles()).thenReturn(apiResponse)
        commsViewModel.articles.value = mockCommsRepository.getArticles()

        val actualResponse = commsViewModel.articles.getOrAwaitValue()

        assertEquals(article, actualResponse.data?.get(0))
        assertEquals(DataStatus.SUCCESS, actualResponse.status)
        assertEquals(1, actualResponse.data?.size)
    }

    @Test
    fun testGetArticlesError() = runBlocking {
        val article = Article(0,"title1","content1",null,null,null, true,null)
        val responseCode = 404
        val responseMessage = "It's broken"
        val apiResponse = ApiData.error(responseCode, responseMessage, listOf(article))

        Mockito.`when`(mockCommsRepository.getArticles()).thenReturn(apiResponse)
        commsViewModel.articles.value = mockCommsRepository.getArticles()

        val actualResponse = commsViewModel.articles.getOrAwaitValue()
        assertEquals(article, actualResponse.data!![0])
        assertEquals(DataStatus.ERROR, actualResponse.status)
        assertEquals(responseMessage, actualResponse.message)
    }

    @Test
    fun testGetCategoriesSuccess() = runBlocking {
        val categoryName = "Summer Camp"
        val category = Category(0, categoryName)
        `when`(mockCommsRepository.getCategories()).thenReturn(ApiData.success(listOf(category)))

        commsViewModel.categories.value = listOf(categoryName)

        val actualResponse = commsViewModel.categories.getOrAwaitValue()
        assertEquals(categoryName, actualResponse[0])
    }

    @Test
    fun testGetCategoriesError() = runBlocking {
        val categoryName = "Summer Camp"
        val category = Category(0, categoryName)
        val responseCode = 404
        val responseMessage = "It's broken"

        `when`(mockCommsRepository.getCategories()).thenReturn(ApiData.error(responseCode, responseMessage, listOf(category)))
        commsViewModel.categories.value = listOf(categoryName)

        val actualResponse = commsViewModel.categories.getOrAwaitValue()
        assertEquals(categoryName, actualResponse[0])
    }

}

