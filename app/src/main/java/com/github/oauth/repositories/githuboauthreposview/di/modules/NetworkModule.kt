package com.github.oauth.repositories.githuboauthreposview.di.modules

import android.content.Context
import com.github.oauth.repositories.githuboauthreposview.remote.BaseInterceptor.Companion.interceptor
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.remote.createOkHttpClient
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_API_URL
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_URL
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named(BASE_URL)
    fun baseUrl(): String {
        return BASE_API_URL
    }

    @Singleton
    @Provides
    fun retrofitService(
        retrofit: Retrofit
    ): RetrofitService {
        return retrofit.create<RetrofitService>()
    }

    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Singleton
    @Provides
    fun getRetrofit(
        @Named(BASE_URL) baseUrl: String,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient(interceptor))
            .build()
    }

    @Singleton
    @Provides
    fun networkStatus(context: Context): NetworkStatus {
        return NetworkStatus(context)
    }
}