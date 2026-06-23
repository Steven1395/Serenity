package com.example.serenity.data.journal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_journal")
data class SleepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayName: String,          // Contoh: "Sen", "Sel"
    val sleepHours: Float,        // Dari Slider 1
    val screenTimeHours: Float,   // Dari Slider 2
    val sleepQuality: String,     // TAMBAHAN: Menyimpan "Bagus" atau "Buruk"
    val timestamp: Long = System.currentTimeMillis()
)