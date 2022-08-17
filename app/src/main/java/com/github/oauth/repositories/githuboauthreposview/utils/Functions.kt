package com.github.oauth.repositories.githuboauthreposview.utils

// Обрезка концовки ссылки на ветки репозитория в строке "{/branch}"
fun cutBranches(url: String?): String {
    return if ((url != null) && (url.indexOf("{/branch}") > -1)) {
        url.substring(0, url.indexOf("{/branch}")) ?: ""
    } else ""
}