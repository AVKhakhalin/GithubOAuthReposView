package com.github.oauth.repositories.githuboauthreposview.di.modules

import android.widget.ImageView
import com.github.oauth.repositories.githuboauthreposview.utils.imageloader.GlideImageLoaderImpl
import com.github.oauth.repositories.githuboauthreposview.utils.imageloader.ImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun glide(): ImageLoader<ImageView> {
        return GlideImageLoaderImpl()
    }
}