package com.example.mealdealapp.domain

import android.util.Log
import com.example.mealdealapp.data.Category
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by shanta on 28/02/2024
 */

const val baseUrl: String = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"

sealed class ProductCatalogResponse{

    data object NetworkError: ProductCatalogResponse()
    data class ServiceError(val message: String): ProductCatalogResponse()
    data class Success(val categories: List<Category>): ProductCatalogResponse()
}

interface ApiService{
    fun getProductCatalog(): Flow<ProductCatalogResponse>
}

class ProductCatalogService @Inject constructor(private val httpClient: HttpClient
): ApiService{
    override fun getProductCatalog(): Flow<ProductCatalogResponse> = flow {
        try {

            emit(ProductCatalogResponse.Success(httpClient.get(baseUrl).body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(e.toCustomExceptions())
        }
    }
}

fun Exception.toCustomExceptions() = when (this) {
    is IOException -> ProductCatalogResponse.NetworkError
    else -> ProductCatalogResponse.ServiceError(this.message?: "Something went wrong, Please try again later.")
}

class MockProductCatalogService():ApiService{

    private lateinit var productCatalogResponse: ProductCatalogResponse

    fun provideProductCatalogService(response: ProductCatalogResponse){
        productCatalogResponse = response
    }

    override fun getProductCatalog(): Flow<ProductCatalogResponse> =  flow{

         productCatalogResponse
    }

}


