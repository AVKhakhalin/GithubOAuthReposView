package com.github.oauth.repositories.githuboauthreposview.utils.resources

import android.content.Context
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String
    fun getContext(): Context
}