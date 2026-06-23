package com.example.serenity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.AppDatabase
import com.example.serenity.data.journal.SleepEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {

    private val sleepDao = AppDatabase.getDatabase(application).sleepDao()

    val sleepDataProvider: Flow<List<SleepEntity>> = sleepDao.getRecentSleepData()

    fun saveSleepData(dayName: String, sleepHours: Float, screenTimeHours: Float, sleepQuality: String) {
        viewModelScope.launch {
            // 1. HAPUS DATA LAMA: Singkirkan data hari ini yang sudah ada sebelumnya
            sleepDao.deleteDataByDay(dayName)

            // 2. BUAT DATA BARU: Set ulang data beserta waktu (timestamp) terupdate
            val newData = SleepEntity(
                dayName = dayName,
                sleepHours = sleepHours,
                screenTimeHours = screenTimeHours,
                sleepQuality = sleepQuality,
                timestamp = System.currentTimeMillis() // Menjaga query DESC tetap mendeteksi ini sebagai yang terbaru
            )

            // 3. SIMPAN DATA: Masukkan data segar ke Room DB
            sleepDao.insertSleepData(newData)
        }
    }
}