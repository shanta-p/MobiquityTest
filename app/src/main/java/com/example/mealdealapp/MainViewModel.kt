package com.example.mealdealapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealdealapp.domain.ApiService
import com.example.mealdealapp.domain.DomainProduct
import com.example.mealdealapp.domain.ProductCatalogResponse
import com.example.mealdealapp.domain.toDomain
import com.example.mealdealapp.ui.MealDealItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shanta on 5/3/24
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    var uiState by mutableStateOf(
        UIState(
            productsList = emptyList(),
            isLoading = false,
            errorMessage = "",
            hasError = false
        )
    )

    init {
        loadItems()
    }

    fun loadItems(){
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            apiService.getProductCatalog()
                .collectLatest{
                    when(it){
                        is ProductCatalogResponse.Success ->
                            uiState = uiState.copy(
                                productsList = it.categories.flatMap { category -> category.toDomain() },
                                isLoading = false
                            )

                        is ProductCatalogResponse.ServiceError -> uiState = uiState.copy(
                            isLoading = false,
                            hasError = true,
                            errorMessage = "Something went wrong, please try again."
                        )
                        is ProductCatalogResponse.NetworkError -> uiState = uiState.copy(
                            isLoading = false,
                            hasError= true,
                            errorMessage = "Please check your internet connection."
                        )

                        else -> {}
                    }
                }
        }
    }

    fun dismissSnackBar(){
        uiState = uiState.copy(
            errorMessage = "",
            hasError =  false
        )
    }

    private fun DomainProduct.toMealDealItem(): MealDealItem {
        return MealDealItem(
            id = this.id,
            categoryName = this.categoryName,
            categoryId = this.categoryId,
            imageUrl = this.imageUrl,
            title = this.title
        )
    }

    fun mealDealList(): List<MealDealItem>{
       return uiState.productsList.map {
            it.toMealDealItem()
        }
    }

    fun fetchItem(id: String?, categoryId: String?): DomainProduct {
       return uiState.productsList.first{ it.id == id && it.categoryId == categoryId}
    }
}

data class UIState(
    val productsList: List<DomainProduct>,
    val isLoading: Boolean,
    val errorMessage: String,
    val hasError: Boolean
)

sealed class Event{
    data class ViewItemDetails(val id: String, val categoryId: String): Event()
}