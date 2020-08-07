package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Article
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Response


class ResponseHandlerUnitTest {

    @Test
    fun testHandleSuccessResponse() {
        val testArticle = Article(null, "title", "content", "imgPath", null, null, null, null)
        val testResponse = Response.success(testArticle)

        val actualResponse = ResponseHandler.handleResponse(testResponse)
        assertEquals(testArticle, actualResponse.data)
    }

    @Test
    fun testHandleErrorResponse() {
        val errorCode = 403
        val erroneousResponse: Response<Article> = Response.error(
            errorCode,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val thrownException = assertThrows<HttpException> {
            ResponseHandler.handleResponse(erroneousResponse)
        }

        assertEquals(errorCode, thrownException.code())
        assertEquals("Response.error()", thrownException.message())
    }
}