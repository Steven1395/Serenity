package com.example.serenity.uiux.natunai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
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
fun AiScreen() {
    // Definisi Warna Tema Serenity
    val BackgroundColor = Color(0xFF2E2559)
    val CardColor = Color(0xFF4C4378)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFD0CCDF)

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
    ) {
        // --- KONTEN UTAMA (Bisa di-scroll jika panjang) ---
        Column(
            modifier = Modifier
                .weight(1f) // Mengisi sisa ruang kosong
                .verticalScroll(rememberScrollState())
                .statusBarsPadding() // Menghindari agar tidak tertabrak jam & baterai HP di atas
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            // Top Bar (Menu & Edit)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu", tint = TextWhite)
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit", tint = TextWhite)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Judul
            Text(
                text = "Selamat datang\ndi NatunAI !",
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Rekomendasi
            Text(
                text = "Berikut rekomendasi harian, untukmu :",
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tidurmu semalam baru dimulai pukul 01.30. Tidur lebih awal bisa bantu tubuhmu memperbaiki diri lebih optimal. Coba targetkan jam 22.30 malam ini, ya. Selain itu, Aku juga mendeteksi nada suara yang menunjukkan stres. Gimana kalau kamu dengarkan musik relaksasi atau coba teknik napas 4-7-8 malam ini?",
                fontFamily = PoppinsFont,
                color = TextGray,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tanya Apa Saja
            Text(
                text = "Tanya apa saja!",
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Baris Tombol Aksi (Analisis & Tips)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Tombol Analisis Gambar
                OutlinedButton(
                    onClick = { },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, TextGray),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFFE91E63), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analisis Gambar", fontFamily = PoppinsFont, color = TextWhite, fontSize = 12.sp)
                }

                // Tombol Tips Tidur Sehat
                OutlinedButton(
                    onClick = { },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, TextGray),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFFFFEB3B), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tips tidur sehat", fontFamily = PoppinsFont, color = TextWhite, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Lainnya (Tengah)
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedButton(
                    onClick = { },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, TextGray),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = CardColor),
                    modifier = Modifier.width(160.dp)
                ) {
                    Text("Lainnya", fontFamily = PoppinsFont, color = TextWhite, fontSize = 14.sp)
                }
            }
        }

        // --- BOTTOM BAR (Kotak Prompt Mengambang / Kapsul) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() // Menghindari garis navigasi HP (home screen indicator)
                .imePadding() // Otomatis naik kalau keyboard muncul
                .padding(horizontal = 16.dp, vertical = 24.dp) // PADDING LUAR: Memberi efek mengambang (margin)
                .background(
                    color = Color(0xFF5E5686),
                    shape = CircleShape // SHAPE: Membuat bentuk melingkar penuh (kapsul)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp), // PADDING DALAM: Jarak antara ikon dengan tepi kapsul
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Attachment (Klip/Tambah)
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Attach",
                    tint = TextWhite
                )
            }

            // Teks Placeholder Obrolan
            Text(
                text = "Mulai Obrolan!",
                fontFamily = PoppinsFont,
                color = TextWhite,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Tombol Kirim / Mic (Warna terang dengan ikon gelap)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2DFEB)), // Warna bulatan abu-abu terang
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        tint = Color(0xFF1A153A) // Warna ikon gelap agar kontras
                    )
                }
            }
        }
    }
}