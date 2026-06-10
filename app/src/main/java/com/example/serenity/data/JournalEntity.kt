package com.example.serenity.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_journal")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayName: String, // Contoh: "Sen", "Sel", "Rab"
    val score: Float,    // Nilai rentang 0.0f sampai 1.0f (untuk persentase tinggi batang)
    val date: String     // Tanggal lengkap untuk pencatatan sistem
)