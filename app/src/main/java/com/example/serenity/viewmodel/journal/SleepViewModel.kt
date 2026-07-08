package com.example.serenity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.AppDatabase
import com.example.serenity.data.journal.SleepEntity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {

    private val sleepDao = AppDatabase.getDatabase(application).sleepDao()

    // 🌟 Ambil UID user yang sedang login saat ini dari Firebase
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    // 🌟 Tambahkan currentUserId ke dalam pemanggilan data terbaru
    val sleepDataProvider: Flow<List<SleepEntity>> = sleepDao.getRecentSleepData(currentUserId)

    fun saveSleepData(dayName: String, sleepHours: Float, screenTimeHours: Float, sleepQuality: String) {
        viewModelScope.launch {
            // 1. HAPUS DATA LAMA: Singkirkan data hari ini yang sudah ada sebelumnya
            // 🌟 Pastikan hanya menghapus data di hari yang sama khusus untuk user ini
            sleepDao.deleteDataByDay(dayName, currentUserId)

            // 2. BUAT DATA BARU: Set ulang data beserta waktu (timestamp) terupdate
            val newData = SleepEntity(
                userId = currentUserId, // 🌟 Masukkan ID user agar tidak bocor ke akun lain
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