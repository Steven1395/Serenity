package com.example.serenity.uiux.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serenity.viewmodel.JournalViewModel
import kotlin.random.Random

@Composable
fun JournalScreen(
    viewModel: JournalViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // Ambil data skor kuesioner dari Room secara realtime
    val databaseScores by viewModel.chartScoresProvider.collectAsState()

    val BackgroundColor = Color(0xFF2E2559)
    val CardColor = Color(0xFF4C4378)
    val TextWhite = Color(0xFFFFFFFF)
    val BarColor = Color(0xFF8B78E6)
    val TextGray = Color(0xFFB3ADCC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp)
            .statusBarsPadding()
    ) {
        Text(text = "Statistik Ketenangan", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Laporan hasil kuesionermu selama 7 hari terakhir.", fontSize = 14.sp, color = TextGray)

        Spacer(modifier = Modifier.height(40.dp))

        // --- KARTU DIAGRAM BATANG ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardColor)
                .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            if (databaseScores.isEmpty()) {
                // Keadaan jika database masih kosong melompong
                Text(
                    text = "Belum ada data kuesioner.\nSilakan klik tombol simulasi di bawah.",
                    color = TextGray,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    databaseScores.forEach { data ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = "${(data.score * 100).toInt()}%",
                                color = TextWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .fillMaxHeight(data.score) // Menggunakan nilai float database asli (0.0f - 1.0f)
                                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                    .background(BarColor)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = data.dayName, color = TextGray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- TOMBOL SIMULASI KUESIONER (UNTUK KEPERLUAN DEMO PRESENTASI) ---
        Text(text = "Simulasi Pengisian Kuesioner", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        val daysOptions = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")

        Button(
            onClick = {
                // Saat diklik, generate hari acak dan skor kuesioner acak (30% - 100%) lalu simpan ke Room
                val randomDay = daysOptions[Random.nextInt(daysOptions.size)]
                val randomScore = Random.nextInt(30, 100) / 100f
                viewModel.addMockScore(randomDay, randomScore)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E5686))
        ) {
            Text("Isi Kuesioner Acak & Simpan ke Room", color = TextWhite)
        }
    }
}