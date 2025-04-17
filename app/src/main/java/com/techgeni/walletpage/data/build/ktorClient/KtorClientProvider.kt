package com.techgeni.walletpage.data.build.ktorClient

import com.techgeni.walletpage.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun providerKtorClient() : HttpClient {

    return HttpClient(CIO) {

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000 // umumiy so‘rov muddati – 60 soniya
            connectTimeoutMillis = 30_000 // ulanish uchun maksimal vaqt – 30 soniya
            socketTimeoutMillis = 30_000  // so‘rov davomida kutish vaqti – 30 soniya
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            level = LogLevel.BODY
        }

        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            header("X-Account-Phone", Constants.USER_PHONE_NUMBER)
        }
    }
}