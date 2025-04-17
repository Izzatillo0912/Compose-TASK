package com.techgeni.walletpage.data.build.ktorClient

import com.techgeni.walletpage.utils.Constants
import com.techgeni.walletpage.utils.Error
import com.techgeni.walletpage.utils.RemoteErrorWithCode
import com.techgeni.walletpage.utils.RemoteResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.SerializationException

class KtorApiClient(val httpClient: HttpClient) {

    suspend inline fun <reified R : Any> get(
        route: String,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): RemoteResult<R, Error> {
        return safeCall {
            httpClient.get {
                url(urlString = Constants.BASE_URL + route)
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
        }
    }

    suspend inline fun <reified M : Any> post(
        route: String,
        body: Any? = null,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): RemoteResult<M, Error> {
        return safeCall {
            httpClient.post {
                url(urlString = Constants.BASE_URL + route)
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                body?.let { setBody(it) }
            }
        }
    }

    suspend inline fun <reified R> safeCall(
        execute: () -> HttpResponse
    ): RemoteResult<R, Error> {

        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteErrorWithCode(Error.Remote.NO_INTERNET_ERROR))
        } catch (e: SerializationException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteErrorWithCode(Error.Remote.SERIALIZATION_ERROR))
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            return RemoteResult.Error(RemoteErrorWithCode(Error.Remote.UNKNOWN))
        }

        return responseToResult(response)
    }

    suspend inline fun <reified R> responseToResult(
        response: HttpResponse
    ): RemoteResult<R, Error> {
        return when (response.status.value) {
            in 200..299 -> RemoteResult.Success(response.body())
            in 400..499 -> RemoteResult.Error(
                RemoteErrorWithCode(
                    Error.Remote.REQUEST_ERROR,
                    response.status.value
                )
            )
            in 500..599 -> RemoteResult.Error(
                RemoteErrorWithCode(
                    Error.Remote.SERVER_ERROR,
                    response.status.value
                )
            )
            else -> RemoteResult.Error(
                RemoteErrorWithCode(
                    Error.Remote.UNKNOWN,
                    response.status.value
                )
            )
        }
    }

}