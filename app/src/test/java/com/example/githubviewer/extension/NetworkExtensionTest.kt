package com.example.githubviewer.extension

import com.example.githubviewer.data.remote.service.MockService
import com.example.githubviewer.utils.MockRetrofitBuilder
import com.example.githubviewer.data.statushandler.Status
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection

class NetworkExtensionTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var fakeService: MockService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        retrofit = MockRetrofitBuilder.build(mockWebServer.url("/"))
        fakeService = MockRetrofitBuilder.buildMockService(retrofit)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun networkCall_success_returnSuccess() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{}")
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            val resp = retrofit.networkCall {
                fakeService.emptyGet().await()
            }
            assertEquals(resp.status, Status.Success)
        }
    }

}