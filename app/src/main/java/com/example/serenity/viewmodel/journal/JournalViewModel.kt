package com.example.serenity.viewmodel.journal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.AppDatabase
import com.example.serenity.data.journal.JournalEntity
import com.example.serenity.data.journal.JournalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository
    val chartScoresProvider: StateFlow<List<JournalEntity>>

    init {
        val journalDao = AppDatabase.Companion.getDatabase(application).journalDao()
        repository = JournalRepository(journalDao)

        // Membalikkan urutan data (reversed) agar urutan hari kronologis dari kiri ke kanan di grafik
        chartScoresProvider = repository.last7DaysScores
            .map { it.reversed() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addMockScore(dayName: String, score: Float) {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.insert(JournalEntity(dayName = dayName, score = score, date = timestamp))
        }
    }

    // --- TAMBAHAN: FUNGSI UNTUK MENYIMPAN HASIL KUESIONER RIIL ---
    fun saveRealQuestionnaireResult(yesCount: Int, totalQuestions: Int) {
        viewModelScope.launch {
            // 1. Hitung persentase desimal (Contoh: 2 "Ya" dari 4 soal = 0.5f atau 50% di grafik)
            val calculatedScore = if (totalQuestions > 0) yesCount.toFloat() / totalQuestions.toFloat() else 0.0f

            // 2. Ambil nama hari pendek otomatis dalam Bahasa Indonesia (Sen, Sel, Rab, Kam, Jum, Sab, Min)
            val dayFormat = SimpleDateFormat("EEE", Locale("id", "ID"))
            val currentDay = dayFormat.format(Date()).replace(".", "") // Jaga-jaga jika OS Android memberi titik (cth: "Sab.")

            // 3. Ambil tanggal hari ini sebagai penanda waktu unik
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timestamp = dateFormat.format(Date())

            // 4. Masukkan ke database Room
            repository.insert(
                JournalEntity(
                    dayName = currentDay,
                    score = calculatedScore,
                    date = timestamp
                )
            )
        }
    }
}