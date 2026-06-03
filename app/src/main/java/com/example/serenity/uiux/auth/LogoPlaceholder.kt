package com.example.serenity.uiux.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Palet Warna dari Desain
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