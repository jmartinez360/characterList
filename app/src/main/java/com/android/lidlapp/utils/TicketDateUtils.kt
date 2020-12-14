package com.android.lidlapp.utils

import java.util.Date
import kotlin.math.abs

interface TicketDateUtils{
    fun getRemainingDays(date: Long): Int
}

class TicketDateUtilsImpl: TicketDateUtils {

    override fun getRemainingDays(date: Long): Int {
        val currentDateMillis = Date().time

        val difference: Long = abs(currentDateMillis - date)
        return (difference / (24 * 60 * 60 * 1000)).toInt()
    }
}