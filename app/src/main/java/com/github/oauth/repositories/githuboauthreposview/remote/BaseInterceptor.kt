package com.github.oauth.repositories.githuboauthreposview.remote

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Custom interceptor to intercept basic responses and to show basic errors to the user
 */
class BaseInterceptor private constructor(): Interceptor {
    private var responseCode: Int = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.d(LOG_TAG, "Запрос: ${response.request}")
        Log.d(LOG_TAG, "Ответ: ${response.networkResponse}")
        Log.d(LOG_TAG, "Заголовок ответа: ${response.headers}")
        responseCode = response.code
        Log.d(LOG_TAG, "Лимит запросов: ${response.headers["x-ratelimit-limit"]}")
        Log.d(LOG_TAG, "Оставшееся количество запросов: ${response.headers["x-ratelimit-remaining"]}")
        Log.d(LOG_TAG, "Количество использованных запросов: ${response.headers["x-ratelimit-used"]}")
        Log.d(LOG_TAG, "Временный id: ${response.headers["x-github-request-id"]}")
        Log.d(LOG_TAG, "Дата запроса: ${response.headers["date"]}")
        Log.d(LOG_TAG, "Код результата запроса: ${getResponseCode()}")
        return response
    }

    fun getResponseCode(): ServerResponseStatusCode {
        var statusCode = ServerResponseStatusCode.UNDEFINED_ERROR
        when (responseCode / 100) {
            1 -> statusCode = ServerResponseStatusCode.INFO
            2 -> statusCode = ServerResponseStatusCode.SUCCESS
            3 -> statusCode = ServerResponseStatusCode.REDIRECTION
            4 -> statusCode = ServerResponseStatusCode.CLIENT_ERROR
            5 -> statusCode = ServerResponseStatusCode.SERVER_ERROR
        }
        return statusCode
    }

    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }

    companion object {
        val interceptor: BaseInterceptor
            get() = BaseInterceptor()
    }
}