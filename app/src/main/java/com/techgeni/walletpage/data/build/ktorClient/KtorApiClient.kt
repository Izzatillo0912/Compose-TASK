package com.techgeni.walletpage.data.build.ktorClient

import com.techgeni.walletpage.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException

class KtorApiClient(val httpClient: HttpClient) {

    suspend inline fun <reified R : Any> get(
        route: String,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): RemoteResult<R, RemoteError> {
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
    ): RemoteResult<M, RemoteError> {
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

    suspend inline fun <reified M : Any> put(
        route: String,
        body: Any? = null,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): RemoteResult<M, RemoteError> {
        return safeCall {
            httpClient.put {
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
    ): RemoteResult<R, RemoteError> {

        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteError.ConnectionError())
        } catch (e: SerializationException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteError.SerializationError())
        } catch (e : ConnectTimeoutException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteError.ConnectionError())
        } catch (e : TimeoutCancellationException) {
            e.printStackTrace()
            return RemoteResult.Error(RemoteError.ConnectionError())
        } catch (e: Exception) {
            e.printStackTrace()
            return when(e.message) {
                "Connection reset by peer" -> RemoteResult.Error(RemoteError.ConnectionError())
                else -> RemoteResult.Error(RemoteError.Unknown(e.message ?: "Unknown error"))
            }
        }

        return responseToResult(response)
    }

    suspend inline fun <reified R> responseToResult(response: HttpResponse): RemoteResult<R, RemoteError> {
        return when (response.status.value) {
            in 200..299 -> RemoteResult.Success(response.body())
            in 400..499 -> RemoteResult.Error(RemoteError.RequestError())
            in 500..599 -> RemoteResult.Error(RemoteError.ServerError("Server ERROR : ${response.status.description} / ${response.status.value}"))
            else -> RemoteResult.Error(RemoteError.Unknown("Unknown ERROR : ${response.status.description} / ${response.status.value}"))
        }
    }

}