package com.example.serenity.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.serenity.data.journal.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    // 🌟 Ubah query dan tambah parameter userId
    @Query("SELECT * FROM table_journal WHERE userId = :userId ORDER BY id DESC LIMIT 7")
    fun getLast7DaysScores(userId: String): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertJournal(journal: JournalEntity): Long

    // 🌟 (Opsional tapi disarankan) Filter juga pakai userId agar tidak salah hapus punya orang
    @Query("DELETE FROM table_journal WHERE id = :journalId AND userId = :userId")
    suspend fun deleteJournalById(journalId: Int, userId: String): Int

    // 🌟 Tambah parameter userId
    @Query("DELETE FROM table_journal WHERE dayName = :targetDay AND userId = :userId")
    suspend fun deleteDataByDay(targetDay: String, userId: String)

    // 🌟 Tambah parameter userId
    @Query("SELECT * FROM table_journal WHERE userId = :userId ORDER BY id DESC LIMIT 1")
    fun getLatestJournal(userId: String): kotlinx.coroutines.flow.Flow<JournalEntity?>
// ...
}