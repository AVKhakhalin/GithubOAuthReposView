package com.github.oauth.repositories.githuboauthreposview.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(interceptor)
    httpClient.addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
    return httpClient.build()
}