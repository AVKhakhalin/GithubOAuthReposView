package com.github.oauth.repositories.githuboauthreposview.remote

import com.github.oauth.repositories.githuboauthreposview.utils.TIME_WAIT_OKHTTP_RESPONSE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(TIME_WAIT_OKHTTP_RESPONSE, TimeUnit.MILLISECONDS)
    httpClient.readTimeout(TIME_WAIT_OKHTTP_RESPONSE, TimeUnit.MILLISECONDS).build()
    httpClient.addInterceptor(interceptor)
    httpClient.addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
    return httpClient.build()
}