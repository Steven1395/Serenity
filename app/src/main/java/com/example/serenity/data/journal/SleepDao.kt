package com.example.serenity.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    suspend fun insertSleepData(sleepEntity: SleepEntity)

    // 🌟 Tambah parameter userId
    @Query("SELECT * FROM sleep_journal WHERE userId = :userId ORDER BY timestamp DESC LIMIT 7")
    fun getRecentSleepData(userId: String): Flow<List<SleepEntity>>

    // 🌟 Tambah parameter userId
    @Query("DELETE FROM sleep_journal WHERE dayName = :targetDay AND userId = :userId")
    suspend fun deleteDataByDay(targetDay: String, userId: String)

    // 🌟 Tambah parameter userId
    @Query("SELECT * FROM sleep_journal WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestSleep(userId: String): Flow<SleepEntity?>
}