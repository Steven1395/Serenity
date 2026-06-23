package com.example.serenity.data.journal

import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {
    val last7DaysScores: Flow<List<JournalEntity>> = journalDao.getLast7DaysScores()

    suspend fun insert(journal: JournalEntity): Long {
        return journalDao.insertJournal(journal)
    }

    // --- TAMBAHAN BARU: Jembatan untuk memanggil fungsi hapus dari DAO ---
    suspend fun deleteDataByDay(dayName: String) {
        journalDao.deleteDataByDay(dayName)
    }
}