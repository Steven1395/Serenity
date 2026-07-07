package com.example.serenity.data.journal

import kotlinx.coroutines.flow.Flow

// Kita tambahkan sleepDao ke dalam constructor repository
class JournalRepository(
    private val journalDao: JournalDao,
    private val sleepDao: SleepDao
) {
    val last7DaysScores: Flow<List<JournalEntity>> = journalDao.getLast7DaysScores()

    // --- FUNGSI BARU: Mengambil 1 data tidur terbaru untuk CherryAI ---
    fun getLatestSleepData(): Flow<SleepEntity?> {
        return sleepDao.getLatestSleep()
        // Catatan: Pastikan di dalam file SleepDao milikmu sudah ada fungsi getLatestSleep() ya!
    }

    // Mengambil 1 data kuesioner psikologis terbaru
    fun getLatestJournalData(): Flow<JournalEntity?> {
        return journalDao.getLatestJournal()
    }

    suspend fun insert(journal: JournalEntity): Long {
        return journalDao.insertJournal(journal)
    }

    suspend fun deleteDataByDay(dayName: String) {
        journalDao.deleteDataByDay(dayName)
    }
}