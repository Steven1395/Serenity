package com.example.serenity.uiux.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R

@Composable
fun JournalScreen() {
    // Definisi Warna
    val BackgroundColor = Color(0xFF2E2559)
    val CardColor = Color(0xFF4C4378)
    val LightPurple = Color(0xFF8B80F9)
    val Pink = Color(0xFFF21B7F)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFD0CDD8)
    val LineColor = Color(0xFF5E5686)

    // Definisi Font Poppins
    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (_: Exception) {
        FontFamily.Default
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 24.dp, vertical = 48.dp) // Sesuaikan dengan safe area
    ) {
        // --- TOP BAR (Back Button & Title) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { /* TODO: Navigasi kembali */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextWhite
                )
            }
        }

        Text(
            text = "Sleep Journal",
            fontFamily = PoppinsFont,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        // --- GRAFIK (CHART AREA) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            // Label Y-Axis & Grid Lines
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val yLabels = listOf("5", "4", "3", "2", "1")
                Text("Jam", fontFamily = PoppinsFont, color = TextGray, fontSize = 10.sp)

                yLabels.forEach { label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = label,
                            fontFamily = PoppinsFont,
                            color = TextGray,
                            fontSize = 12.sp,
                            modifier = Modifier.width(24.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(LineColor)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hari",
                        fontFamily = PoppinsFont,
                        color = TextGray,
                        fontSize = 10.sp,
                        modifier = Modifier.width(32.dp)
                    )
                }
            }

            // Batang Grafik (Bars) & X-Axis
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, bottom = 20.dp), // Hindari label Y dan Hari
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // Dummy Data: (Tinggi Tidur, Tinggi ScreenTime, Label Hari)
                val chartData = listOf(
                    Triple(0.4f, 0.6f, "T"),
                    Triple(0.8f, 0.65f, "W"),
                    Triple(0.65f, 0.85f, "T"),
                    Triple(0.4f, 0.65f, "F"),
                    Triple(0.5f, 0.8f, "S")
                )

                chartData.forEach { (sleepRatio, screenRatio, day) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Bar Waktu Tidur
                            Box(
                                modifier = Modifier
                                    .width(16.dp)
                                    .fillMaxHeight(sleepRatio)
                                    .background(LightPurple)
                            )
                            // Bar Screentime
                            Box(
                                modifier = Modifier
                                    .width(16.dp)
                                    .fillMaxHeight(screenRatio)
                                    .background(Pink)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // Label Hari
                        Text(
                            text = day,
                            fontFamily = PoppinsFont,
                            color = TextWhite,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- LEGEND (Keterangan Warna) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(color = LightPurple, label = "Waktu Tidur", font = PoppinsFont)
            Spacer(modifier = Modifier.width(32.dp))
            LegendItem(color = Pink, label = "Screentime", font = PoppinsFont)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- INSIGHT TEXT ---
        Text(
            text = "Kualitas tidur kamu cenderung menurun di akhir minggu. Pola ini bisa dipengaruhi oleh screen time yang meningkat pada malam hari.",
            fontFamily = PoppinsFont,
            color = TextWhite,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- SUMMARY CARDS ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryCard(
                title = "Rerata Waktu Tidur",
                value = "6 h 15 m",
                badgeText = "Good",
                badgeColor = LightPurple,
                cardColor = CardColor,
                font = PoppinsFont,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Rerata Screentime",
                value = "7 h 15 m",
                badgeText = "Bad",
                badgeColor = Pink,
                cardColor = CardColor,
                font = PoppinsFont,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- BOTTOM BUTTONS ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = LightPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text("Report", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = LightPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text("Reminder", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

// Komponen Pembantu untuk Legend
@Composable
fun LegendItem(color: Color, label: String, font: FontFamily) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Efek Glow Sederhana
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color, CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontFamily = font, color = Color.White, fontSize = 12.sp)
    }
}

// Komponen Pembantu untuk Kotak Rerata (Card)
@Composable
fun SummaryCard(
    title: String,
    value: String,
    badgeText: String,
    badgeColor: Color,
    cardColor: Color,
    font: FontFamily,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(cardColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontFamily = font,
                color = Color.White,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontFamily = font,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeColor)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                Text(
                    text = badgeText,
                    fontFamily = font,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}