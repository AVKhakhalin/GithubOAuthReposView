package com.github.oauth.repositories.githuboauthreposview.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// Обрезка концовки ссылки на ветки репозитория в строке "{/branch}"
fun cutBranches(url: String?): String {
    return if ((url != null) && (url.indexOf("{/branch}") > -1)) {
        url.substring(0, url.indexOf("{/branch}")) ?: ""
    } else ""
}

// Преобразование строки в дату
@SuppressLint("SimpleDateFormat")
fun convertStringDateToDate(stringDate: String): Long {
    val dateFormatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
    try {
        val date: Date = dateFormatter.parse(stringDate)
        return date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0L
}

// Формирование корректного окончания времени
fun setCorrectMinutes(minutes: Long): String {
    val minutesStr: String = "$minutes"
    if ((minutes > 10L) && (minutes < 20L))
        return "$minutes минут"
    return when(minutesStr[minutesStr.length - 1].toString().toLong()) {
        1L -> "$minutes минуту"
        in 2L..4L -> "$minutes минуты"
        in 5L..9L -> "$minutes минут"
        else -> {"$minutes минут"}
    }
}