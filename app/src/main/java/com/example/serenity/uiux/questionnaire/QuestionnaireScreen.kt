package com.example.serenity.uiux.questionnaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // <-- TAMBAHKAN IMPORT INI
import com.example.serenity.R
import com.example.serenity.viewmodel.journal.JournalViewModel // <-- TAMBAHKAN IMPORT INI

@Composable
fun QuestionnaireScreen(
    onFinished: () -> Unit,
    viewModel: JournalViewModel = viewModel() // <--- HUBUNGKAN KE VIEWMODEL DI SINI
) {
    // Definisi Warna Ungu dari Desain
    val DeepPurple = Color(0xFF422C73)

    // Definisi Font Poppins
    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (e: Exception) {
        FontFamily.Default
    }

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
            .background(Color.White) // paksa putih
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isFinished) {
            // JUDUL
            Text(
                text = "Kuisioner Harian",
                fontFamily = PoppinsFont,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPurple,
                modifier = Modifier.padding(top = 24.dp)
            )

            // Pendorong elastis agar pertanyaan pas di tengah
            Spacer(modifier = Modifier.weight(1f))

            // TEKS PERTANYAAN
            Text(
                text = questions[currentQuestionIndex],
                fontFamily = PoppinsFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = DeepPurple,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // PILIHAN JAWABAN (RADIO BUTTON)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Ya"),
                        onClick = { answers[currentQuestionIndex] = "Ya" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DeepPurple,
                            unselectedColor = DeepPurple
                        )
                    )
                    Text(text = "Ya", fontFamily = PoppinsFont, color = DeepPurple)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Tidak"),
                        onClick = { answers[currentQuestionIndex] = "Tidak" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DeepPurple,
                            unselectedColor = DeepPurple
                        )
                    )
                    Text(text = "Tidak", fontFamily = PoppinsFont, color = DeepPurple)
                }
            }

            // Pendorong elastis agar tombol turun ke bawah
            Spacer(modifier = Modifier.weight(1.2f))

            val onNextClick: () -> Unit = {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    isFinished = true

                    // --- PROSES PERHITUNGAN & PENYIMPANAN DATA RIIL ---
                    val yesCount = answers.count { it == "Ya" }
                    val totalQuestions = questions.size

                    // Simpan ke database melalui fungsi baru di ViewModel
                    viewModel.saveRealQuestionnaireResult(yesCount = yesCount, totalQuestions = totalQuestions)

                    println("Semua jawaban disimpan ke Room DB: ${answers.toList()}")
                }
            }

            // TOMBOL NAVIGASI
            if (currentQuestionIndex == 0) {
                Button(
                    onClick = onNextClick,
                    enabled = answers[currentQuestionIndex].isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(56.dp)
                ) {
                    Text(
                        text = "Selanjutnya",
                        fontFamily = PoppinsFont,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { currentQuestionIndex-- },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Kembali",
                            fontFamily = PoppinsFont,
                            color = DeepPurple,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onNextClick,
                        enabled = answers[currentQuestionIndex].isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = if (currentQuestionIndex == questions.size - 1) "Selesai" else "Selanjutnya",
                            fontFamily = PoppinsFont,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        } else {
            // TAMPILAN SELESAI
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Terima Kasih!",
                fontFamily = PoppinsFont,
                color = DeepPurple,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Data kuesioner berhasil dianalisis oleh Cherry AI untuk mengoptimalkan tidurmu.\n\nHasil: ${answers.toList()}",
                fontFamily = PoppinsFont,
                color = DeepPurple,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 2. SAKLAR KE DASHBOARD: Ditambahkan agar setelah membaca rangkuman, user bisa masuk ke Dashboard Utama
            Button(
                onClick = onFinished, // <--- Memanggil fungsi callback navigasi dari MainActivity
                colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Masuk Ke Dashboard",
                    fontFamily = PoppinsFont,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}