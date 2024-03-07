package com.example.mealdealapp.data

import kotlinx.serialization.SerialName

/**
 * Created by shanta on 28/02/2024
 */

@kotlinx.serialization.Serializable
data class Product(
    @SerialName("id")
    val id: String,

    @SerialName("categoryId")
    val categoryId: String,

    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String,

    @SerialName("description")
    val description: String,

    @SerialName("salePrice")
    val salePrice: SalePrice
)

@kotlinx.serialization.Serializable
data class SalePrice(

    @SerialName("amount")
    val amount: String,

    @SerialName("currency")
    val currency: String
)

@kotlinx.serialization.Serializable
data class Category(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("products")
    val products: List<Product>
)
