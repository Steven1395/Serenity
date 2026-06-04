package com.example.serenity.uiux.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. PERBAIKAN WARNA: Menghapus huruf 'UL' di akhir
val TextDarkPurple = Color(0xFF2B2250)
val LightLavenderBg = Color(0xFFD2CFFC)
val ProfileLavender = Color(0xFF8884D8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // --- HEADER: Judul Aplikasi & Profil ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Serenity",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkPurple
            )

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(ProfileLavender)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- SEARCH BAR ---
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari", color = TextDarkPurple.copy(alpha = 0.8f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextDarkPurple.copy(alpha = 0.8f)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightLavenderBg,
                unfocusedContainerColor = LightLavenderBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- SUB-HEADER: Dashboard Utama ---
        Text(
            text = "Dashboard Utama",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDarkPurple
        )

        Spacer(modifier = Modifier.height(16.dp))

// --- DAFTAR MENU ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = LightLavenderBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            // Menggunakan Icons.Default.List sebagai pengganti Assignment
            DashboardMenuItem(icon = Icons.Default.List, title = "Kuisioner Aktivitas") {
                // TODO: Navigasi ke Kuesioner
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Menggunakan Icons.Default.Star sebagai pengganti Bedtime (karena berhubungan dengan malam)
            DashboardMenuItem(icon = Icons.Default.Star, title = "Pantauan Tidur") {
                // TODO: Navigasi ke Pantauan Tidur
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Menggunakan Icons.Default.Face sebagai pengganti SmartToy/Robot (mewakili AI)
            DashboardMenuItem(icon = Icons.Default.Face, title = "Tanya NatunAI") {
                // TODO: Navigasi ke NatunAI
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Menggunakan Icons.Default.PlayArrow sebagai pengganti MusicNote
            DashboardMenuItem(icon = Icons.Default.PlayArrow, title = "Musik Serenity") {
                // TODO: Navigasi ke Musik
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Settings sudah ada di Core bawaan
            DashboardMenuItem(icon = Icons.Default.Settings, title = "Pengaturan") {
                // TODO: Navigasi ke Pengaturan
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Menggunakan Icons.Default.Info untuk Bantuan
            DashboardMenuItem(icon = Icons.Default.Info, title = "Bantuan") {
                // TODO: Navigasi ke Bantuan
            }
            HorizontalDivider(color = Color.White, thickness = 1.dp)

            // Info sudah ada di Core bawaan
            DashboardMenuItem(icon = Icons.Default.Info, title = "Tentang Kami") {
                // TODO: Navigasi ke Tentang Kami
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DashboardMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = TextDarkPurple,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = TextDarkPurple,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}