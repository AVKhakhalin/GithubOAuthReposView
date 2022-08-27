package com.github.oauth.repositories.githuboauthreposview.utils

const val DATABASE_NAME: String = "database.db"
const val BASE_API_URL: String = "https://api.github.com"
const val BASE_API_REPO_URL: String = "https://api.github.com/repos"
const val LOG_TAG: String = "mylogs"
const val DELAY_TIME: Long = 2000L
// Установка scope на доступ к закрытым и открытым репозиториям
const val AUTHORISE_URL: String = "https://github.com/login/oauth/authorize?client_id=a3c530e1e2f03aeb3bdf&scope=repo%20user"
const val BASE_TOKEN_URL: String = "http://githuboauth.ddns.net/success/result.php?login="
const val TARGET_USER_NAME_URL: String = "https://gist.github.com/"
const val LOGOUT_GITHUB: String = "https://github.com/logout"
const val EMPTY_NAME_MASK: String = "mine"
const val LOGOUT_MASK_ONE: String = "https://github.com"
const val LOGOUT_MASK_TWO: String = "https://github.com/"
const val BASE_URL: String = "BASE_URL"
const val URL_AUTHORISE_TO_PING: String = "http://githuboauth.ddns.net"
const val URL_GITHUB_TO_PING: String = "https://github.com"
const val TIME_WAIT_OKHTTP_RESPONSE: Long = 100000L
const val READ_TIMEOUT: Int = 10000
const val LIMIT_REQUEST_TAG: String = "x-ratelimit-limit"
const val REMAINING_REQUEST_TAG: String = "x-ratelimit-remaining"
const val LAST_DATE_REQUEST_TAG: String = "date"
const val DATE_FORMAT: String = "EEE, dd MMM yyyy HH:mm:ss zzz"
const val DATE_TO_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
const val OUTPUT_DATE_FORMAT: String = "yyyy.MM.dd  HH:mm:ss"
const val MILLISECONDS_IN_HOUR: Long = 3600000L
const val DEFAULT_NUMBER_GITHUB_LIMIT_REQUEST: Int = 60
const val FORMAT_FUTURE_TIME: String = "HH:mm"
const val LOCALE_LANGUAGE_DATE_FORMAT: String = "en"
const val TOKEN_NAME: String = "Token"
const val BRANCH_NAME: String = "branch"
const val BRANCHES_NAME: String = "branches"
const val COMMITS_NAME: String = "commits"
const val SHA_NAME: String = "sha"
// SharedPreferences
const val SHARED_PREFERENCES_KEY: String = "Shared Preferences"
const val SHARED_PREFERENCES_USER_LOGIN: String = "Shared Preferences User Login"
const val SHARED_PREFERENCES_NUMBER_LIMIT_REQUESTS: String =
    "Shared Preferences Number Limit Requests"
const val SHARED_PREFERENCES_NUMBER_REMAINING_REQUESTS: String =
    "Shared Preferences Number Remaining Requests"
const val SHARED_PREFERENCES_REQUESTS_TIMES_NUMBER: String =
    "Shared Preferences Requests Times Number"
const val SHARED_PREFERENCES_REQUEST_TIME: String = "Shared Preferences Request Time_"
const val SHARED_PREFERENCES_AC_TOK: String = "Shared Preferences Ac Tok"
// Параметры сохранения аватарки пользователя
const val IMAGE_QUALITY: Int = 100
const val IMAGE_FORMAT: String = "jpg"
const val IMAGE_CACHE_FOLDER_NAME: String = "CacheAvatars"
// Типы сообщений о критических ошибках при работе приложения
enum class MAIN_ERRORS {
    NO_INTERNET,
    OAUTH_SERVER_ERROR,
    GITHUB_SERVER_ERROR,
    CLIENT_ERROR
}
// Типы сообщений при обмене сообщениями с сервером github.com
enum class ServerResponseStatusCode {
    INFO,
    SUCCESS,
    REDIRECTION,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNDEFINED_ERROR
}