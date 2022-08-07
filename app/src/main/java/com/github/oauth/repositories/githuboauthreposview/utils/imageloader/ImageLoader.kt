package com.github.oauth.repositories.githuboauthreposview.utils.imageloader

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}