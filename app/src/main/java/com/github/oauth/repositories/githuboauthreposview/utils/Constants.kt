package com.github.oauth.repositories.githuboauthreposview.utils

const val DATABASE_NAME: String = "database.db"
const val BASE_API_URL: String = "https://api.github.com"
const val BASE_API_REPO_URL: String = "https://api.github.com/repos"
const val LOG_TAG: String = "mylogs"
const val DELAY_TIME: Long = 1000L
const val AUTHORISE_URL: String = "https://github.com/login/oauth/authorize?client_id=a3c530e1e2f03aeb3bdf&redirect_uri=http://githuboauth.ddns.net/custom/oauth-github.php&scope=user&response_type=code&state="
const val TARGET_USER_NAME_URL: String = "https://gist.github.com/"
const val LOGOUT_GITHUB: String = "https://github.com/logout"
const val EMPTY_NAME_MASK: String = "mine"
const val LOGOUT_MASK_ONE: String = "https://github.com"
const val LOGOUT_MASK_TWO: String = "https://github.com/"
const val BASE_URL = "BASE_URL"
const val URL_AUTHORISE_TO_PING: String = "http://githuboauth.ddns.net"
const val URL_GITHUB_TO_PING: String = "https://github.com"
const val TIME_WAIT_OKHTTP_RESPONSE: Long = 200000
const val READ_TIMEOUT: Int = 10000
const val LIMIT_REQUEST_TAG: String = "x-ratelimit-limit"
const val REMAINING_REQUEST_TAG: String = "x-ratelimit-remaining"
const val LAST_DATE_REQUEST_TAG: String = "date"
// SharedPreferences
const val SHARED_PREFERENCES_KEY: String = "Shared Preferences"
const val SHARED_PREFERENCES_USER_LOGIN: String = "Shared Preferences User Login"
const val SHARED_PREFERENCES_NUMBER_LIMIT_REQUESTS: String =
    "Shared Preferences Number Limit Requests"
const val SHARED_PREFERENCES_NUMBER_REMAINING_REQUESTS: String =
    "Shared Preferences Number Remaining Requests"
const val SHARED_PREFERENCES_LAST_DATE_REQUEST: String = "Shared Preferences Last Date Request"
// Параметры сохранения аватарки пользователя
const val IMAGE_QUALITY: Int = 100
const val IMAGE_FORMAT: String = "jpg"
const val IMAGE_CACHE_FOLDER_NAME: String = "CacheAvatars"
// Типы сообщений о критических ошибках
enum class MAIN_ERRORS {
    NO_INTERNET,
    OAUTH_SERVER_ERROR,
    GITHUB_SERVER_ERROR
}