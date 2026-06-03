package com.example.serenity.uiux

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionnaireScreen() {
    val questions = remember {
        listOf(
            "Apakah kamu sering menggunakan smartphone?\n(lebih dari 4 jam per hari)",
            "Apakah kamu mengonsumsi kafein atau kopi dalam 6 jam sebelum tidur?",
            "Apakah jadwal tidurmu teratur dalam 3 hari terakhir?",
            "Apakah kamu merasa segar saat bangun tidur pagi ini?"
        )
    }

    val answers = remember { mutableStateListOf(*Array(questions.size) { "" }) }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
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

            // Pertanyaan menggunakan rata tengah (Center)
            Text(
                text = questions[currentQuestionIndex],
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Ya"),
                        onClick = { answers[currentQuestionIndex] = "Ya" }
                    )
                    Text(text = "Ya")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Tidak"),
                        onClick = { answers[currentQuestionIndex] = "Tidak" }
                    )
                    Text(text = "Tidak")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            val onNextClick: () -> Unit = {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    isFinished = true
                    println("Semua jawaban disimpan: ${answers.toList()}")
                }
            }

            if (currentQuestionIndex == 0) {
                Button(
                    onClick = onNextClick,
                    enabled = answers[currentQuestionIndex].isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Selanjutnya")
                }
            } else {

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { currentQuestionIndex-- },
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text(text = "Kembali")
                    }

                    Button(
                        onClick = onNextClick,
                        enabled = answers[currentQuestionIndex].isNotEmpty(),
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    ) {
                        Text(
                            text = if (currentQuestionIndex == questions.size - 1) "Selesai" else "Selanjutnya"
                        )
                    }
                }
            }
        } else {
            Text(
                text = "Terima Kasih!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Data kuesioner berhasil dianalisis oleh Natuna AI untuk mengoptimalkan tidurmu.\n\nHasil: ${answers.toList()}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}