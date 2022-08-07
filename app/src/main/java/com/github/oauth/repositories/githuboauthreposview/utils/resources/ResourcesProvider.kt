package com.github.oauth.repositories.githuboauthreposview.utils.resources

import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String
}