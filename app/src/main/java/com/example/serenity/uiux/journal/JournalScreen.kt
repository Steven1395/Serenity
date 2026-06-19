package com.example.serenity.uiux.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serenity.R
import com.example.serenity.viewmodel.journal.JournalViewModel

@Composable
fun JournalScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: JournalViewModel = viewModel()
) {
    // Skema Warna Premium
    val BackgroundColor = Color(0xFF1D1737)
    val CardBackgroundColor = Color(0xFF332A55)
    val BarColor = Color(0xFF9279F8)
    val TextMuted = Color(0xFFB3AEC6)

    // Definisi Font Poppins
    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (e: Exception) {
        FontFamily.Default
    }

    // Mengambil data riil dari Room DB secara realtime
    val chartData by viewModel.chartScoresProvider.collectAsState()

    // 1. MEMBUAT LIST HARI FIX (Senin - Minggu)
    val daysOfWeek = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // JUDUL UTAMA STATISTIK
        Text(
            text = "Statistik Ketenangan",
            fontFamily = PoppinsFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // SUB-JUDUL
        Text(
            text = "Laporan hasil kuesionermu selama 7 hari terakhir.",
            fontFamily = PoppinsFont,
            fontSize = 14.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(32.dp))

        // KARTU UTAMA WADAH GRAFIK
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 28.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // Looping berdasarkan urutan hari yang sudah FIX Senin - Minggu
                daysOfWeek.forEach { day ->

                    // Mencari data kuesioner terakhir yang cocok dengan nama hari ini
                    val matchedEntity = chartData.lastOrNull {
                        it.dayName.equals(day, ignoreCase = true)
                    }

                    // Konversi skor desimal (0.0f - 1.0f) ke bentuk persen (0 - 100)
                    val scorePercentage = if (matchedEntity != null) {
                        (matchedEntity.score * 100).toInt().coerceIn(0, 100)
                    } else {
                        0
                    }

                    // Struktur Kolom Tunggal untuk Satu Batang Grafik
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Teks Persentase di atas Batang
                        Text(
                            text = "$scorePercentage%",
                            fontFamily = PoppinsFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Area Batang Grafik Dinamis
                        Box(
                            modifier = Modifier
                                .height(180.dp)
                                .width(22.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            // Batang Ungu yang tingginya mengikuti scorePercentage
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(if (scorePercentage > 0) scorePercentage / 100f else 0.02f)
                                    .background(
                                        color = if (scorePercentage > 0) BarColor else BarColor.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Label Nama Hari yang sudah fix di bawahnya
                        Text(
                            text = day,
                            fontFamily = PoppinsFont,
                            fontSize = 12.sp,
                            color = TextMuted,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}