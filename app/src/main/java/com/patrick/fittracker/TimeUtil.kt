package com.patrick.fittracker

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    @JvmStatic
    fun StampToDate(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun DateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)

        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time
    }

    @JvmStatic
    fun CalendarStampToDate(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun AnalysisStampToDate(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒

        val simpleDateFormat = SimpleDateFormat("MM-dd", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun TestDateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        // 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time
    }
}