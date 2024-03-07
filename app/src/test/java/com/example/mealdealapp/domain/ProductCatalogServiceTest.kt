package com.example.mealdealapp.domain

import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlinx.coroutines.flow.collectLatest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by shanta on 6/3/24
 */
class ProductCatalogServiceTest{


    @Test
    fun canReturnServiceErrorResponse() = testApplication {
        val client = createClient {}

        val service = ProductCatalogService(client)


        val response = client.get{
            expectSuccess = false
        }
        service.getProductCatalog().collectLatest {
            assertEquals(it.javaClass,  ProductCatalogResponse.ServiceError(response.toString()).javaClass)
        }
    }
}