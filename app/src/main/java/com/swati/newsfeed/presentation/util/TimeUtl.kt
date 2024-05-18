package com.swati.newsfeed.presentation.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object TimeUtl {
    fun String.toEpochMillis(): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.parse(this)?.time ?: 0
    }

    fun utcToLocal(
        dateFormatInput: String,
        dateFormatOutput: String,
        datesToConvert: String?,
    ): String? {
        var dateToReturn = datesToConvert
        try {
            datesToConvert?.let { dateToConvert ->
                dateToReturn = SimpleDateFormat(dateFormatInput).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.parse(dateToConvert)?.let {
                    SimpleDateFormat(dateFormatOutput).apply {
                        timeZone = TimeZone.getDefault()
                    }.format(it)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }
}