package com.example.music_buddy

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    fun getTimeDifference(rawJsonDate: String?): String {
        var time = ""
        val igFormat = "EEE MMM dd HH:mm:ss zzz yyyy"
        val format = SimpleDateFormat(igFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val diff = (System.currentTimeMillis() - format.parse(rawJsonDate).time) / 1000
            if (diff < 5)
                time = "Just now"
            else if (diff < 60)
                time = String.format(Locale.ENGLISH, "%ds", diff)
            else if (diff < 60 * 60)
                time = String.format(Locale.ENGLISH, "%dm", diff / 60)
            else if (diff < 60 * 60 * 24)
                time = String.format(Locale.ENGLISH, "%dh", diff / (60 * 60))
            else if (diff < 60 * 60 * 24 * 30)
                time = String.format(Locale.ENGLISH, "%dd", diff / (60 * 60 * 24))
            else {
                val now = Calendar.getInstance()
                val then = Calendar.getInstance()
                then.time = format.parse(rawJsonDate)
                time = if (now[Calendar.YEAR] == then[Calendar.YEAR]) {
                    (then[Calendar.DAY_OF_MONTH].toString() + " " + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US))
                } else {
                    (then[Calendar.DAY_OF_MONTH].toString() + " " + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + (then[Calendar.YEAR] - 2000).toString())
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    fun getTimeStamp(rawJsonDate: String?): String {
        var time = ""
        val igFormat = "EEE MMM dd HH:mm:ss zzz yyyy"
        val format = SimpleDateFormat(igFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val then = Calendar.getInstance()
            then.time = format.parse(rawJsonDate)
            val date = then.time
            val format1 = SimpleDateFormat("h:mm a \u00b7 dd MMM yy")
            time = format1.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    fun getDate(rawJsonDate: String?): String {
        var date = ""
        val igFormat = "EEE MMM dd HH:mm:ss zzz yyyy"
        val format = SimpleDateFormat(igFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val then = Calendar.getInstance()
            then.time = format.parse(rawJsonDate)
            val time = then.time
            val format1 = SimpleDateFormat("MM/dd/yy")
            date = format1.format(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getTime(rawJsonDate: String?): String {
        var time = ""
        val igFormat = "EEE MMM dd HH:mm:ss zzz yyyy"
        val format = SimpleDateFormat(igFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val then = Calendar.getInstance()
            then.time = format.parse(rawJsonDate)
            val date = then.time
            val format1 = SimpleDateFormat("h:mm a")
            time = format1.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }
}