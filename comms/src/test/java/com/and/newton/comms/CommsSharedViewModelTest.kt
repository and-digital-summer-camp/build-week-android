package com.and.newton.comms

import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Matchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class CommsSharedViewModelTest {
    @Mock
     var commsRepository: CommsRepository = Mockito.mock(CommsRepository::class.java)

    lateinit var commsViewModel: CommsSharedViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.commsViewModel = CommsSharedViewModel(this.commsRepository)
    }

    @Test
    fun testGetArticles() = runBlocking {
        val article:Article = Article(0,"title1","content1",null,null,null, true,null)
        Mockito.`when`(commsRepository.getArticles()).thenReturn(listOf(article))

        assertEquals(commsRepository.getArticles(),listOf(article));

 }


    @Test
    fun testGetCategories() = runBlocking {
        val category:Category = Category(0, "Summer Camp")
        Mockito.`when`(commsRepository.getCategories()).thenReturn(listOf(category))

        assertEquals(commsRepository.getCategories(),listOf(category));
    }
}