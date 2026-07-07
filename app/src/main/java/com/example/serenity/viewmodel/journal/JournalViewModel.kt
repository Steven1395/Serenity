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
        // 1. Panggil databasenya dulu
        val database = AppDatabase.getDatabase(application)

        // 2. Ambil kedua DAO yang dibutuhkan
        val journalDao = database.journalDao()
        val sleepDao = database.sleepDao()

        // 3. Masukkan KEDUA DAO tersebut ke dalam Repository
        repository = JournalRepository(journalDao, sleepDao)

        // Membalikkan urutan data (reversed) agar urutan hari kronologis dari kiri ke kanan di grafik
        chartScoresProvider = repository.last7DaysScores
            .map { it.reversed() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addMockScore(dayName: String, score: Float) {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            // Ditambahkan parameter notes default agar fungsi dummy/mock ini tidak error
            repository.insert(JournalEntity(dayName = dayName, score = score, date = timestamp, notes = "Data uji coba (mock)"))
        }
    }

    // --- PERBARUAN LENGKAP: Menyimpan Skor & Teks Catatan untuk CherryAI ---
    fun saveRealQuestionnaireResult(answers: List<String>) {
        viewModelScope.launch {
            var positiveCount = 0f
            var catatanAi = "Kondisi psikologis semalam: "

            // Pertanyaan 1: Makan berat/alkohol (Jawaban sehat: "Tidak")
            if (answers.getOrNull(0) == "Tidak") {
                positiveCount += 1f
            } else {
                catatanAi += "Konsumsi makan berat/alkohol. "
            }

            // Pertanyaan 2: Konsumsi kafein (Jawaban sehat: "Tidak")
            if (answers.getOrNull(1) == "Tidak") {
                positiveCount += 1f
            } else {
                catatanAi += "Konsumsi kafein. "
            }

            // Pertanyaan 3: Cemas/Overthinking (Jawaban sehat: "Tidak")
            if (answers.getOrNull(2) == "Tidak") {
                positiveCount += 1f
            } else {
                catatanAi += "Merasa cemas/overthinking. "
            }

            // Pertanyaan 4: Bangun merasa segar (Jawaban sehat: "Ya")
            if (answers.getOrNull(3) == "Ya") {
                positiveCount += 1f
                catatanAi += "Bangun dengan segar."
            } else {
                catatanAi += "Bangun kurang berenergi."
            }

            // Jika semua jawaban bernilai sehat, buat kalimat yang positif dan rapi
            if (positiveCount == 4f) {
                catatanAi = "Kondisi psikologis sangat baik, tidak ada keluhan stres atau konsumsi yang mengganggu tidur."
            }

            // Hitung persentase desimal dari 4 pertanyaan
            val calculatedScore = positiveCount / 4f

            // Ambil nama hari pendek otomatis
            val dayFormat = SimpleDateFormat("EEE", Locale("id", "ID"))
            val currentDay = dayFormat.format(Date()).replace(".", "")

            // Ambil tanggal hari ini
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timestamp = dateFormat.format(Date())

            // 1. Hapus data kuesioner lama di hari yang sama agar tidak menumpuk (duplikat)
            repository.deleteDataByDay(currentDay)

            // 2. Masukkan data baru ke database Room beserta Catatan Ringkasan untuk AI
            repository.insert(
                JournalEntity(
                    dayName = currentDay,
                    score = calculatedScore,
                    date = timestamp,
                    notes = catatanAi // <-- Sekarang sudah sukses menyetorkan teks ke database!
                )
            )
        }
    }
}