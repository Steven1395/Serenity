package com.example.serenity.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.serenity.data.journal.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    // Hanya ambil maksimal 7 data terbaru untuk keperluan grafik mingguan
    @Query("SELECT * FROM table_journal ORDER BY id DESC LIMIT 7")
    fun getLast7DaysScores(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertJournal(journal: JournalEntity): Long

    @Query("DELETE FROM table_journal WHERE id = :journalId")
    suspend fun deleteJournalById(journalId: Int): Int

    // --- TAMBAHAN BARU: Menghapus data berdasarkan nama hari ---
    @Query("DELETE FROM table_journal WHERE dayName = :targetDay")
    suspend fun deleteDataByDay(targetDay: String)
}