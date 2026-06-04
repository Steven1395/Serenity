package com.example.serenity.uiux.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 👇 INI PUSAT WARNA UNTUK SEMUA HALAMAN AUTH
val DarkPurpleBg = Color(0xFF312A5C)
val LightBlueBtn = Color(0xFFC0DAFA)
val LavenderBtn = Color(0xFF8884D8)
val DarkShadow = Color(0xFF1E193E)

@Composable
fun LogoPlaceholder() {
    // Lingkaran putih luar
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .background(Color(0xFFF3F2EE)),
        contentAlignment = Alignment.Center
    ) {
        // Lingkaran biru gelap dalam
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Color(0xFF152C40)),
            contentAlignment = Alignment.Center
        ) {
            // Kotak placeholder logo
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray)
            )
        }
    }
}