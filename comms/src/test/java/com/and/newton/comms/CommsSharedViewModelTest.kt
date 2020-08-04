package com.and.newton.comms

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CommsSharedViewModelTest {
    @Mock
     var commsRepository: CommsRepository = Mockito.mock(CommsRepository::class.java)

    lateinit var commsViewModel: CommsSharedViewModel


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.commsViewModel = CommsSharedViewModel(this.commsRepository)
    }



    @Test
    fun testGetArticlesFromRepo() = runBlocking {
        val article:Article = Article(0,"title1","content1",null,null,null, true,null)
        Mockito.`when`(commsRepository.getArticles()).thenReturn(listOf(article))

        assertEquals(commsRepository.getArticles(),listOf(article));

        commsViewModel.articles.value = commsRepository.getArticles()


        assertEquals(article, commsViewModel.articles.getOrAwaitValue()[0])

        assertEquals(1, commsViewModel.articles.getOrAwaitValue().size)
    }


    @Test
    fun testGetArticle()  {
        val article:Article = Article(0,"title1","content1",null,null,null, true,null)

        commsViewModel.article.value =  article

        assertEquals(commsViewModel.article.getOrAwaitValue(), article)


    }



    @Test
    fun testGetCategories() = runBlocking {
        val category:Category = Category(0, "Summer Camp")
        Mockito.`when`(commsRepository.getCategories()).thenReturn(listOf(category))

        assertEquals(commsRepository.getCategories(),listOf(category));
    }
}

