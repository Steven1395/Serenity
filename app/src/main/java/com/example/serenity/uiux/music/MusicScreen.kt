package com.example.serenity.uiux.music

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share

@Composable
fun MusicScreen() {
    // 1. Definisi Warna yang konsisten dengan tema Serenity
    val BackgroundColor = Color(0xFF2E2559)
    val CardColor = Color(0xFF4C4378)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFB3ADCC)
    val SliderTrackColor = Color(0xFF5E5686)

    // 2. Definisi Font Poppins
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
            .padding(horizontal = 24.dp, vertical = 48.dp) // Safe area
    ) {
        // --- TOP BAR (Tombol Back) ---
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
            Text(
                text = "Back",
                fontFamily = PoppinsFont,
                color = TextWhite,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- COVER ALBUM UTAMA (Lingkaran Besar) ---
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // TIPS: Untuk presentasi besok, jika belum ada gambar, biarkan kotak abu-abu ini.
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .clip(CircleShape)
                    .background(CardColor)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- INFO LAGU & IKON AKSI ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Go Sleep",
                    fontFamily = PoppinsFont,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 24.sp
                )
                Text(
                    text = "The Sleeping Lofi",
                    fontFamily = PoppinsFont,
                    color = TextGray,
                    fontSize = 14.sp
                )
            }

            // Deretan Ikon Kanan (Like, Download, Share)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Hati (Favorite)
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Filled.Favorite,
                        contentDescription = "Like",
                        tint = TextWhite
                    )
                }

                // Icon Download (Panah ke bawah tebal/fill)
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = "Download",
                        tint = TextWhite
                    )
                }

                // Icon Share
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = TextWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- PROGRESS BAR ---
        Slider(
            value = 0.15f, // Angka statis untuk keperluan demo (15%)
            onValueChange = { },
            colors = SliderDefaults.colors(
                thumbColor = TextWhite,
                activeTrackColor = TextWhite,
                inactiveTrackColor = SliderTrackColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0:25", fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
            Text(text = "3:15", fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- TOMBOL KONTROL PEMUTAR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🔀", color = TextWhite, fontSize = 20.sp) // Shuffle
            Text("⏮", color = TextWhite, fontSize = 24.sp) // Previous

            // Tombol Play Besar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(TextGray),
                contentAlignment = Alignment.Center
            ) {
                Text("▶", color = BackgroundColor, fontSize = 24.sp) // Play
            }

            Text("⏭", color = TextWhite, fontSize = 24.sp) // Next
            Text("🔁", color = TextWhite, fontSize = 20.sp) // Repeat
        }

        // Pendorong agar elemen "Up Next" selalu menempel di bagian bawah layar
        Spacer(modifier = Modifier.weight(1f))

        // --- UP NEXT (Antrean Lagu) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Up Next", fontFamily = PoppinsFont, color = TextGray, fontSize = 16.sp)
            Text(text = "Queue >", fontFamily = PoppinsFont, color = TextWhite, fontSize = 14.sp)
        }

        // Kartu Lagu Selanjutnya
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CardColor)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder Cover Mini
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SliderTrackColor)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Young",
                    fontFamily = PoppinsFont,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 16.sp
                )
                Text(
                    text = "The Chainsmokers",
                    fontFamily = PoppinsFont,
                    color = TextGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}