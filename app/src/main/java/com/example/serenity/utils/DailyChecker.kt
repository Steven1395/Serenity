package com.example.serenity.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyChecker(context: Context) {
    private val prefs = context.getSharedPreferences("serenity_prefs", Context.MODE_PRIVATE)

    private fun getTodayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }

    fun isAlreadyFilledToday(): Boolean {
        val lastFilledDate = prefs.getString("LAST_FILLED_DATE", "")
        return lastFilledDate == getTodayDate()
    }

    fun markAsFilledToday() {
        prefs.edit().putString("LAST_FILLED_DATE", getTodayDate()).apply()
    }
}