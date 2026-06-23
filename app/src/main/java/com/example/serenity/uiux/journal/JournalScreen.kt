package com.example.serenity.uiux.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.serenity.viewmodel.SleepViewModel

@Composable
fun JournalScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: JournalViewModel = viewModel(),
    sleepViewModel: SleepViewModel = viewModel()
) {
    // Skema Warna Premium
    val BackgroundColor = Color(0xFF1D1737)
    val CardBackgroundColor = Color(0xFF332A55)
    val BarColor = Color(0xFF9279F8)
    val TextMuted = Color(0xFFB3AEC6)
    val PinkHighlight = Color(0xFFE91E63)

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
    val sleepData by sleepViewModel.sleepDataProvider.collectAsState(initial = emptyList())

    val todaySleepEntity = sleepData.firstOrNull()

    // MEMBUAT LIST HARI FIX (Senin - Minggu)
    val daysOfWeek = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")

    // PERHITUNGAN RATA-RATA OTOMATIS DARI DATABASE
    val avgSleep = if (sleepData.isNotEmpty()) sleepData.map { it.sleepHours }.average() else 0.0
    val avgScreenTime = if (sleepData.isNotEmpty()) sleepData.map { it.screenTimeHours }.average() else 0.0

    // Tambahkan scroll state agar layar bisa digulir ke bawah
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState) // MEMBUAT LAYAR BISA DI-SCROLL
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // ==========================================
        // 1. GRAFIK STATISTIK KETENANGAN (KUESIONER)
        // ==========================================
        Text(
            text = "Statistik Ketenangan",
            fontFamily = PoppinsFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Laporan hasil kuesionermu selama 7 hari terakhir.",
            fontFamily = PoppinsFont,
            fontSize = 14.sp,
            color = TextMuted
        )
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 28.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                daysOfWeek.forEach { day ->
                    val matchedEntity = chartData.lastOrNull { it.dayName.equals(day, ignoreCase = true) }
                    val scorePercentage = if (matchedEntity != null) {
                        (matchedEntity.score * 100).toInt().coerceIn(0, 100)
                    } else { 0 }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "$scorePercentage%",
                            fontFamily = PoppinsFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Box(modifier = Modifier.height(180.dp).width(22.dp), contentAlignment = Alignment.BottomCenter) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(if (scorePercentage > 0) scorePercentage / 100f else 0.02f)
                                    .background(color = if (scorePercentage > 0) BarColor else BarColor.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = day, fontFamily = PoppinsFont, fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ==========================================
        // 2. KARTU INDIKATOR KUALITAS TIDUR
        // ==========================================
        Card(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Kualitas Tidur Hari Ini", color = Color.White, fontFamily = PoppinsFont, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                val isQualityBagus = todaySleepEntity?.sleepQuality == "Bagus"
                Card(
                    colors = CardDefaults.cardColors(containerColor = if (isQualityBagus) BarColor else PinkHighlight),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        text = todaySleepEntity?.sleepQuality ?: "Belum Isi",
                        color = Color.White,
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ==========================================
        // 3. KARTU RATA-RATA MINGGUAN (TIDUR & HP)
        // ==========================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Rata-rata Tidur
            Card(
                modifier = Modifier.weight(1f).height(80.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Rata-rata Tidur", color = TextMuted, fontSize = 12.sp, fontFamily = PoppinsFont)
                    Text(
                        text = String.format("%.1f Jam", avgSleep),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFont
                    )
                }
            }

            // Rata-rata Screen Time
            Card(
                modifier = Modifier.weight(1f).height(80.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Rata-rata Main HP", color = TextMuted, fontSize = 12.sp, fontFamily = PoppinsFont)
                    Text(
                        text = String.format("%.1f Jam", avgScreenTime),
                        color = PinkHighlight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFont
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ==========================================
        // 4. GRAFIK JAM TIDUR (MAX 12 JAM)
        // ==========================================
        Text(
            text = "Pantauan Jam Tidur",
            fontFamily = PoppinsFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 28.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                daysOfWeek.forEach { day ->
                    val matchedEntity = sleepData.lastOrNull { it.dayName.equals(day, ignoreCase = true) }
                    val sleepVal = matchedEntity?.sleepHours ?: 0f
                    val maxSleep = 12f // Tinggi maksimal batang diset untuk 12 Jam

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if (sleepVal > 0) String.format("%.1f", sleepVal) else "0",
                            fontFamily = PoppinsFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Box(modifier = Modifier.height(140.dp).width(22.dp), contentAlignment = Alignment.BottomCenter) {
                            val heightFraction = (sleepVal / maxSleep).coerceIn(0.02f, 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(if (sleepVal > 0) heightFraction else 0.02f)
                                    .background(color = if (sleepVal > 0) BarColor else BarColor.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = day, fontFamily = PoppinsFont, fontSize = 12.sp, color = TextMuted)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ==========================================
        // 5. GRAFIK SCREEN TIME (MAX 8 JAM)
        // ==========================================
        Text(
            text = "Pantauan Screen Time",
            fontFamily = PoppinsFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 28.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                daysOfWeek.forEach { day ->
                    val matchedEntity = sleepData.lastOrNull { it.dayName.equals(day, ignoreCase = true) }
                    val screenVal = matchedEntity?.screenTimeHours ?: 0f
                    val maxScreen = 8f // Tinggi maksimal batang diset untuk 8 Jam

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if (screenVal > 0) String.format("%.1f", screenVal) else "0",
                            fontFamily = PoppinsFont,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Box(modifier = Modifier.height(140.dp).width(22.dp), contentAlignment = Alignment.BottomCenter) {
                            val heightFraction = (screenVal / maxScreen).coerceIn(0.02f, 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(if (screenVal > 0) heightFraction else 0.02f)
                                    .background(color = if (screenVal > 0) PinkHighlight else PinkHighlight.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = day, fontFamily = PoppinsFont, fontSize = 12.sp, color = TextMuted)
                    }
                }
            }
        }

        // Ruang kosong tambahan di paling bawah agar tidak terpotong saat scroll
        Spacer(modifier = Modifier.height(64.dp))
    }
}