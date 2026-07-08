package com.example.serenity.data.natunai // Sesuaikan dengan foldermu

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history_table")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(), // Untuk mengurutkan chat lama ke baru
    val sessionId: String // Untuk membedakan obrolan hari ini dengan obrolan minggu lalu (kalau diklik dari menu hamburger)
)