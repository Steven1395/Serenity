package com.example.serenity.data.journal

import kotlinx.coroutines.flow.Flow

// Kita tambahkan sleepDao ke dalam constructor repository
class JournalRepository(
    private val journalDao: JournalDao,
    private val sleepDao: SleepDao
) {
    // 🌟 UBAH DARI 'val' MENJADI 'fun' agar bisa menerima parameter userId
    fun getLast7DaysScores(userId: String): Flow<List<JournalEntity>> {
        return journalDao.getLast7DaysScores(userId)
    }

    // 🌟 Tambah parameter userId
    fun getLatestSleepData(userId: String): Flow<SleepEntity?> {
        return sleepDao.getLatestSleep(userId)
    }

    // 🌟 Tambah parameter userId
    fun getLatestJournalData(userId: String): Flow<JournalEntity?> {
        return journalDao.getLatestJournal(userId)
    }

    // (Insert tidak perlu diubah karena objek entity-nya nanti sudah bawa userId dari ViewModel)
    suspend fun insert(journal: JournalEntity): Long {
        return journalDao.insertJournal(journal)
    }

    // 🌟 Tambah parameter userId
    suspend fun deleteDataByDay(dayName: String, userId: String) {
        journalDao.deleteDataByDay(dayName, userId)
    }
}