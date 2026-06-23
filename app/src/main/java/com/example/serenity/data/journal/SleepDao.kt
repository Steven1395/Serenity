package com.example.serenity.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    suspend fun insertSleepData(sleepEntity: SleepEntity)

    // Mengambil 7 data terakhir untuk ditampilkan di grafik nanti
    @Query("SELECT * FROM sleep_journal ORDER BY timestamp DESC LIMIT 7")
    fun getRecentSleepData(): Flow<List<SleepEntity>>

    // --- TAMBAHAN BARU ---
    // Menghapus data lama di hari yang sama agar tidak terjadi penumpukan/duplikasi grafik
    @Query("DELETE FROM sleep_journal WHERE dayName = :targetDay")
    suspend fun deleteDataByDay(targetDay: String)
}