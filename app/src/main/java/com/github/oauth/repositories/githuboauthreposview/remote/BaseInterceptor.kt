package com.github.oauth.repositories.githuboauthreposview.remote

import android.annotation.SuppressLint
import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.utils.*
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Custom interceptor to intercept basic responses and to show basic errors to the user
 */
class BaseInterceptor: Interceptor {
    /* Исходные данные */ //region
    // Класс для временного хранения пользовательских данных
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()
    // Код результата запроса
    private var responseCode: Int = 0
    //endregion

    @SuppressLint("SimpleDateFormat")
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
        // Установка лимита запросов
        response.headers[LIMIT_REQUEST_TAG]?.let {
            userChoose.setNumberLimitRequest(it.toInt())
        }
        // Установка количества оставшихся запросов
        response.headers[REMAINING_REQUEST_TAG]?.let {
            userChoose.setNumberRemainingRequest(it.toInt())
        }
        // Установка времени последнего запроса
        response.headers[LAST_DATE_REQUEST_TAG]?.let {
            userChoose.setRequestTime(convertStringDateToMillisec(it))
        }
        // Установка кода результата запроса
        userChoose.setResponseCode(getResponseCode())
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
}