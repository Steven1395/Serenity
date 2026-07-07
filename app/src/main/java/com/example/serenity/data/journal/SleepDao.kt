package com.example.serenity.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    suspend fun insertSleepData(sleepEntity: SleepEntity)

    // Mengambil 7 data terakhir untuk grafik mingguan
    @Query("SELECT * FROM sleep_journal ORDER BY timestamp DESC LIMIT 7")
    fun getRecentSleepData(): Flow<List<SleepEntity>>

    // Menghapus data lama di hari yang sama agar tidak duplikat
    @Query("DELETE FROM sleep_journal WHERE dayName = :targetDay")
    suspend fun deleteDataByDay(targetDay: String)

    // Mengambil 1 data tidur paling baru untuk dianalisis oleh CherryAI
    @Query("SELECT * FROM sleep_journal ORDER BY timestamp DESC LIMIT 1")
    fun getLatestSleep(): Flow<SleepEntity?>
}