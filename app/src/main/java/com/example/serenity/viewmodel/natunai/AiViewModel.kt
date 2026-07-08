package com.example.serenity.viewmodel.natunai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.BuildConfig
import com.example.serenity.data.AppDatabase
import com.example.serenity.data.journal.JournalEntity
import com.example.serenity.data.journal.JournalRepository
import com.example.serenity.data.journal.SleepEntity
import com.example.serenity.data.natunai.ChatEntity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth // 🌟 Tambahan Import Firebase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AiViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = JournalRepository(database.journalDao(), database.sleepDao())
    private val chatDao = database.chatDao()

    // 🌟 Ambil UID user yang sedang login saat ini dari Firebase
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    // State Sesi Aktif
    private val _currentSessionId = MutableStateFlow("")
    val currentSessionId: StateFlow<String> = _currentSessionId.asStateFlow()

    private val _chatHistory = MutableStateFlow<List<ChatEntity>>(emptyList())
    val chatHistory: StateFlow<List<ChatEntity>> = _chatHistory.asStateFlow()

    // State untuk menampung semua daftar riwayat sesi di hamburger menu
    private val _allSessions = MutableStateFlow<List<String>>(emptyList())
    val allSessions: StateFlow<List<String>> = _allSessions.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _dailyRecommendation = MutableStateFlow("Sedang menganalisis datamu...")
    val dailyRecommendation: StateFlow<String> = _dailyRecommendation.asStateFlow()

    private var latestSleepSession: SleepEntity? = null
    private var latestJournalSession: JournalEntity? = null
    private var currentChatJob: Job? = null // Job untuk memantau chat secara real-time

    init {
        // 1. Ambil daftar semua sesi yang pernah ada di database berdasarkan User ID
        viewModelScope.launch {
            // 🌟 Mengoper currentUserId ke DAO agar list sesi di menu hamburger tidak bercampur antar akun
            chatDao.getAllSessions(currentUserId).collect { sessions ->
                _allSessions.value = sessions

                // Jika aplikasi baru dibuka dan belum memilih sesi, muat sesi terakhir (paling baru)
                if (_currentSessionId.value.isEmpty()) {
                    if (sessions.isNotEmpty()) {
                        switchSession(sessions.first())
                    } else {
                        startNewSession() // Jika DB kosong murni, buat sesi pertama
                    }
                }
            }
        }

        // Ambil data tidur secara real-time
        viewModelScope.launch {
            // 🌟 Memasukkan currentUserId sesuai perubahan pada JournalRepository sebelumnya
            repository.getLatestSleepData(currentUserId).collect { sleepData ->
                latestSleepSession = sleepData
                updateDailyRecommendation()
            }
        }

        // Ambil data kuesioner secara real-time
        viewModelScope.launch {
            // 🌟 Memasukkan currentUserId sesuai perubahan pada JournalRepository sebelumnya
            repository.getLatestJournalData(currentUserId).collect { journalData ->
                latestJournalSession = journalData
                updateDailyRecommendation()
            }
        }
    }

    // Fungsi untuk membuat obrolan/sesi baru
    fun startNewSession() {
        val newId = UUID.randomUUID().toString()
        switchSession(newId)
    }

    // Fungsi untuk berpindah sesi chat ketika diklik dari menu hamburger
    fun switchSession(sessionId: String) {
        _currentSessionId.value = sessionId
        currentChatJob?.cancel() // Batalkan pemantauan sesi lama

        // Mulai pantau data chat dari sesi yang baru dipilih
        currentChatJob = viewModelScope.launch {
            // 🌟 Tambahkan currentUserId di sini sebagai parameter kedua
            chatDao.getChatsBySession(sessionId, currentUserId).collect { list ->
                _chatHistory.value = list
            }
        }
    }

    private fun updateDailyRecommendation() {
        val sleep = latestSleepSession
        if (sleep != null) {
            _dailyRecommendation.value = "Tidurmu pada hari ${sleep.dayName} berdurasi ${sleep.sleepHours} jam dengan kualitas ${sleep.sleepQuality}. Screen time-mu tercatat ${sleep.screenTimeHours} jam. Jangan lupa ngobrol dengan CherryAI tentang harimu!"
        } else {
            _dailyRecommendation.value = "Kamu belum mengisi jurnal tidur hari ini. Yuk, isi kuisionermu terlebih dahulu agar CherryAI bisa memberikan analisis!"
        }
    }

    private fun getDynamicGeminiModel(): GenerativeModel {
        val sleepContext = latestSleepSession?.let {
            "Durasi tidur semalam: ${it.sleepHours} jam, screen time: ${it.screenTimeHours} jam, dan kualitas tidur: ${it.sleepQuality}."
        } ?: "Belum ada data jam tidur."

        val journalContext = latestJournalSession?.let {
            "Catatan kuesioner: ${it.notes}"
        } ?: "Belum ada data psikologis."

        return GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = BuildConfig.CHERRY_API_KEY,
            systemInstruction = content {
                text("Kamu adalah CherryAI, asisten kesehatan tidur yang hangat dan suportif. " +
                        "Gunakan data pengguna berikut untuk memberikan saran yang sangat personal.\n\n" +
                        "--- DATA PENGGUNA HARI INI ---\n" +
                        "$sleepContext\n" +
                        "$journalContext\n" +
                        "-----------------------------\n\n" +
                        "Jika di catatan kuesioner pengguna minum kafein atau merasa stres, sarankan teknik relaksasi. " +
                        "Jawab dengan ramah, berempati, dan gunakan bahasa Indonesia sehari-hari.")
            }
        )
    }

    fun updateInputText(newText: String) {
        _inputText.value = newText
    }

    fun sendMessage() {
        val currentText = _inputText.value.trim()
        if (currentText.isNotEmpty()) {
            _inputText.value = ""

            viewModelScope.launch {
                val userChat = ChatEntity(
                    userId = currentUserId, // 🌟 Masukkan ID user saat menyimpan chat pengguna
                    text = currentText,
                    isFromUser = true,
                    sessionId = _currentSessionId.value
                )
                chatDao.insertChat(userChat)
                getGeminiResponse(currentText, getDynamicGeminiModel())
            }
        }
    }

    private suspend fun getGeminiResponse(userPrompt: String, model: GenerativeModel) {
        try {
            val response = model.generateContent(userPrompt)
            val aiReplyText = response.text ?: "Maaf, ada gangguan pada sistem CherryAI."

            val aiChat = ChatEntity(
                userId = currentUserId, // 🌟 Masukkan ID user saat menyimpan balasan AI
                text = aiReplyText,
                isFromUser = false,
                sessionId = _currentSessionId.value
            )
            chatDao.insertChat(aiChat)

        } catch (e: Exception) {
            val errorChat = ChatEntity(
                userId = currentUserId, // 🌟 Masukkan ID user saat menyimpan pesan error
                text = "Error: ${e.localizedMessage}",
                isFromUser = false,
                sessionId = _currentSessionId.value
            )
            chatDao.insertChat(errorChat)
        }
    }
}