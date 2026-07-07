package com.example.serenity.data.natunai

import java.util.UUID

// Data class ini berfungsi sebagai cetakan untuk setiap satu balon (bubble) chat
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(), // ID unik otomatis
    val text: String,                              // Isi pesan teksnya
    val isFromUser: Boolean                        // True jika dari pengguna, False jika dari AI
)