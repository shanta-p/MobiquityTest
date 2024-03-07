package com.example.mealdealapp.domain

import com.example.mealdealapp.data.Category
import com.example.mealdealapp.data.SalePrice

/**
 * Created by shanta on 5/3/24
 */


data class DomainProduct(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val price: String,
    val categoryName: String,
    val categoryId: String
)
fun Category.toDomain(): List<DomainProduct> {

   return this.products.map { item ->

            DomainProduct(
            id = item.id,
            title = item.name,
            imageUrl = baseUrl.plus(item.url),
            categoryId = item.categoryId,
            categoryName = this.name,
            price = item.salePrice.currency.plus(item.salePrice.amount)
        )
    }
}

fun SalePrice.toPriceWithCurrencySymbol(): String {
    return when(this.currency){
        "EUR" -> "€${this.amount}"
        "GBP" -> "£${this.amount}"
        "USD" -> "$${this.amount}"
        else -> this.currency.plus(" ${this.amount}")
    }
}