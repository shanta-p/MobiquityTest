package com.example.mealdealapp

/**
 * Created by shanta on 7/3/24
 */

import androidx.lifecycle.viewModelScope
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;

import com.example.mealdealapp.domain.ApiService;
import com.example.mealdealapp.domain.ProductCatalogResponse;
import com.example.mealdealapp.domain.toDomain;
import com.example.mealdealapp.MainViewModel;

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatcher
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    public val rule = MockitoJUnit.rule();

    @Mock
    private lateinit var mockApiService: ApiService

    private val testDispatcher = StandardTestDispatcher()
    private val viewModelScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(mockApiService)
//        viewModel.viewModelScope = viewModelScope
        Dispatchers.setMain(testDispatcher) // Replace main dispatcher for test
    }

    @Test
    fun `loadItems should update uiState with success data`() = runBlockingTest {
        // Mock successful API response
        val mockResponse = ProductCatalogResponse.Success(emptyList())
//        `when`(mockApiService.getProductCatalog()).thenReturn(mockResponse)

        // Call loadItems
        viewModel.loadItems()

        // Advance coroutine to completion
        advanceUntilIdle()

        // Assert UI state is updated correctly
        val expectedState = viewModel.uiState.copy(
            productsList = mockResponse.categories.flatMap { it.toDomain() },
            isLoading = false
        )
        assertEquals(expectedState, viewModel.uiState)
    }

    @Test
    fun `loadItems should update uiState with error on service error`() = runBlockingTest {
        // Mock service error
//        `when`(mockApiService.getProductCatalog()).thenReturn(ProductCatalogResponse.ServiceError)

        // Call loadItems
        viewModel.loadItems()

        // Advance coroutine to completion
        advanceUntilIdle()

        // Assert UI state is updated with error message
        val expectedErrorMessage = "Something went wrong, please try again."
        assertTrue(viewModel.uiState.hasError)
        assertEquals(expectedErrorMessage, viewModel.uiState.errorMessage)
    }

    @Test
    fun `loadItems should update uiState with error on network error`() = runBlockingTest {
        // Mock network error
//        `when`(mockApiService.getProductCatalog()).thenReturn(ProductCatalogResponse.NetworkError)

        // Call loadItems
        viewModel.loadItems()

        // Advance coroutine to completion
        advanceUntilIdle()

        // Assert UI state is updated with error message
        val expectedErrorMessage = "Please check your internet connection."
        assertTrue(viewModel.uiState.hasError)
        assertEquals(expectedErrorMessage, viewModel.uiState.errorMessage)
    }
}
