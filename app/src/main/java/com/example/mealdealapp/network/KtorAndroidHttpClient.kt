package com.example.mealdealapp.network

import android.util.Log
import com.example.mealdealapp.domain.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by shanta on 29/02/2024
 */

private const val TIME_OUT = 30_000

val ktorAndroidHttpClient = HttpClient(Android) {

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

    install(Logging){
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor -> ", message)
            }
        }

        level = LogLevel.ALL
    }

    install(ResponseObserver){
        onResponse { response -> Log.d("HTTP status -> ", "${response.status}") }
    }

    install(DefaultRequest){
        url(baseUrl)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}