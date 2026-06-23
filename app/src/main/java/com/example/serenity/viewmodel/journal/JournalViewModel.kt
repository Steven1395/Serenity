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

    // --- PERBARUAN: Menghapus data hari ini sebelum menyimpan yang baru ---
    fun saveRealQuestionnaireResult(answers: List<String>) {
        viewModelScope.launch {
            var positiveCount = 0f

            // Pertanyaan 1: Makan berat/alkohol (Jawaban sehat: "Tidak")
            if (answers.getOrNull(0) == "Tidak") positiveCount += 1f
            // Pertanyaan 2: Konsumsi kafein (Jawaban sehat: "Tidak")
            if (answers.getOrNull(1) == "Tidak") positiveCount += 1f
            // Pertanyaan 3: Cemas/Overthinking (Jawaban sehat: "Tidak")
            if (answers.getOrNull(2) == "Tidak") positiveCount += 1f
            // Pertanyaan 4: Bangun merasa segar (Jawaban sehat: "Ya")
            if (answers.getOrNull(3) == "Ya") positiveCount += 1f

            // Hitung persentase desimal dari 4 pertanyaan
            val calculatedScore = positiveCount / 4f

            // Ambil nama hari pendek otomatis
            val dayFormat = SimpleDateFormat("EEE", Locale("id", "ID"))
            val currentDay = dayFormat.format(Date()).replace(".", "")

            // Ambil tanggal hari ini
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timestamp = dateFormat.format(Date())

            // 1. Hapus data kuesioner di hari yang sama agar tidak menumpuk
            repository.deleteDataByDay(currentDay)

            // 2. Masukkan data baru ke database Room
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