package com.github.oauth.repositories.githuboauthreposview.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// Обрезка концовки ссылки на ветки репозитория в строке "{/branch}"
fun cutBranches(url: String?): String {
    return if ((url != null) && (url.indexOf("{/$BRANCH_NAME}") > -1)) {
        url.substring(0, url.indexOf("{/$BRANCH_NAME}")) ?: ""
    } else ""
}

// Преобразование строки с датой в миллисекунды
@SuppressLint("SimpleDateFormat")
fun convertStringDateToMillisec(stringDate: String): Long {
    val dateFormatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
    try {
        val date: Date? = dateFormatter.parse(stringDate)
        if (date != null)
            return date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0L
}

// Преобразование строки с датой в строку с датой в нужном формате
@SuppressLint("SimpleDateFormat")
fun convertStringDateToUsefulDateString(stringDate: String): String {
    val stringDate: String = stringDate.replace("Z", ".000Z")
    val defaultTimezone = TimeZone.getDefault().id
    try {
        val date: Date? = SimpleDateFormat(DATE_TO_DATE_FORMAT)
            .parse(stringDate.replace("Z$".toRegex(), "+0000"))
        if (date != null)
            return SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
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