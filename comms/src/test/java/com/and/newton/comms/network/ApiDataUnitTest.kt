package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Article
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ApiDataUnitTest {

    @Test
    fun testSuccess() {
        val article = Article(null, null, null, null, null, null, null, null)
        val actualResponse = ApiData.success(article)
        assertEquals(article, actualResponse.data)
        assertEquals(DataStatus.SUCCESS, actualResponse.status)
        assertNull(actualResponse.message)
        assertNull(actualResponse.responseCode)
    }

    @Test
    fun testFailure() {
        val errorCode = 404
        val msg = "It's broken"
        val article = Article(null, null, null, null, null, null, null, null)
        val actualResponse = ApiData.error(errorCode, msg, article)
        assertEquals(article, actualResponse.data)
        assertEquals(DataStatus.ERROR, actualResponse.status)
        assertEquals(msg, actualResponse.message)
        assertEquals(errorCode, actualResponse.responseCode)
    }
}