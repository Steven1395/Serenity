package com.example.naptune.uiux

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionnaireScreen() {
    val questions = remember {
        listOf(
            "Apakah kamu sering menggunakan smartphone? (lebih dari 4 jam perhari)",
            "Apakah kamu mengonsumsi kafein atau kopi dalam 6 jam sebelum tidur?",
            "Apakah jadwal tidurmu teratur dalam 3 hari terakhir?",
            "Apakah kamu merasa segar saat bangun tidur pagi ini?"
        )
    }

    // 1. State untuk menyimpan seluruh jawaban (awalnya kosong semua)
    // mutableStateListOf memastikan UI otomatis update kalau ada jawaban yang diubah
    val answers = remember { mutableStateListOf(*Array(questions.size) { "" }) }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isFinished) {
            Text(
                text = "Kuesioner Harian (${currentQuestionIndex + 1}/${questions.size})",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = questions[currentQuestionIndex],
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        // Cek jawaban spesifik di indeks saat ini
                        selected = (answers[currentQuestionIndex] == "Yes"),
                        onClick = { answers[currentQuestionIndex] = "Yes" }
                    )
                    Text(text = "Yes")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        // Cek jawaban spesifik di indeks saat ini
                        selected = (answers[currentQuestionIndex] == "No"),
                        onClick = { answers[currentQuestionIndex] = "No" }
                    )
                    Text(text = "No")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // 2. Barisan Tombol Bawah (Back dan Next)
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Tombol Back (Hanya dirender kalau bukan di pertanyaan pertama)
                if (currentQuestionIndex > 0) {
                    OutlinedButton(
                        onClick = { currentQuestionIndex-- },
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text(text = "Back")
                    }
                } else {
                    // Spacer kosong agar tombol Next tetap di kanan dan ukurannya konsisten
                    Spacer(modifier = Modifier.weight(1f).padding(end = 8.dp))
                }

                // Tombol Next / Finish
                Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            isFinished = true
                            // Tampilkan semua jawaban di Logcat saat selesai
                            println("Semua jawaban disimpan: ${answers.toList()}")
                        }
                    },
                    // Tombol mati kalau jawaban di nomor ini masih kosong
                    enabled = answers[currentQuestionIndex].isNotEmpty(),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text(
                        text = if (currentQuestionIndex == questions.size - 1) "Finish" else "Next Question"
                    )
                }
            }
        } else {
            // Halaman Selesai
            Text(
                text = "Terima Kasih!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Data kuesioner berhasil dianalisis oleh Natuna AI untuk mengoptimalkan tidurmu.\n\nHasil: ${answers.toList()}",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}