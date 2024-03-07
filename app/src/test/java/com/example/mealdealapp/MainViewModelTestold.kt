package com.example.mealdealapp

import com.example.mealdealapp.data.Category
import com.example.mealdealapp.data.Product
import com.example.mealdealapp.data.SalePrice
import com.example.mealdealapp.domain.ApiService
import com.example.mealdealapp.domain.MockProductCatalogService
import com.example.mealdealapp.domain.ProductCatalogResponse
import com.example.mealdealapp.domain.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertEquals


/**
 * Created by shanta on 6/3/24
 */
class MainViewModelTestOld{

    @JvmField
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val apiService: ApiService = MockProductCatalogService()
    private val viewModel = MainViewModel(apiService)
    private val response = listOf(
        Category(id = "id1", name = "name1", description = "",
            products = listOf(Product(id = "product1", name = "productName1", categoryId = "category1", salePrice = SalePrice(amount = "1", currency = "EUR"), url = "url", description = "desc"),
                Product(id = "product2", name = "productName2", categoryId = "category1", salePrice = SalePrice(amount = "1", currency = "EUR"), url = "url", description = "desc"))),
        Category(id = "id2", name = "name2", description = "",
            products = listOf(Product(id = "product1", name = "productName1", categoryId = "category2", salePrice = SalePrice(amount = "1", currency = "EUR"), url = "url", description = "desc"),
                Product(id = "product1", name = "productName1", categoryId = "category2", salePrice = SalePrice(amount = "1", currency = "EUR"), url = "url", description = "desc"))))

    @Test
    fun canFetchList() = runBlocking {

        (apiService as MockProductCatalogService).provideProductCatalogService(ProductCatalogResponse.Success(response))

            viewModel.loadItems()

        assertEquals(viewModel.uiState.productsList, response.flatMap { category -> category.toDomain()})
    }
}