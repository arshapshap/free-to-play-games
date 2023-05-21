package com.example.games.presentation.utils

import java.util.*

fun Date.toCalendar(): Calendar {
    val cal: Calendar = Calendar.getInstance()
    cal.time = this
    return cal
}