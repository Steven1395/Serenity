package com.example.serenity.data.natunai

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    // Menyimpan chat baru (Tidak perlu diubah, karena entity-nya sudah bawa userId)
    @Insert
    suspend fun insertChat(chat: ChatEntity)

    // 🌟 UBAH INI: Tambahkan filter userId agar tidak menarik chat orang lain di sesi yang sama
    @Query("SELECT * FROM chat_history_table WHERE sessionId = :sessionId AND userId = :userId ORDER BY timestamp ASC")
    fun getChatsBySession(sessionId: String, userId: String): Flow<List<ChatEntity>>

    // 🌟 UBAH INI: Tambahkan filter userId agar menu hamburger hanya menampilkan riwayat milik user yang login
    @Query("SELECT DISTINCT sessionId FROM chat_history_table WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllSessions(userId: String): Flow<List<String>>
}