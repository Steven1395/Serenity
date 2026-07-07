package com.example.serenity.data.natunai

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    // Menyimpan chat baru
    @Insert
    suspend fun insertChat(chat: ChatEntity)

    // Mengambil riwayat chat berdasarkan sesi obrolan
    @Query("SELECT * FROM chat_history_table WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getChatsBySession(sessionId: String): Flow<List<ChatEntity>>

    // Mengambil daftar sesi untuk ditampilkan di Hamburger Menu (Riwayat)
    @Query("SELECT DISTINCT sessionId FROM chat_history_table ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<String>>
}