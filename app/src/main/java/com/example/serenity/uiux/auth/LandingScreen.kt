package com.example.serenity.uiux.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPurpleBg)
    ) {
        // Bintang-bintang (Manual Positioning)
        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700UL), modifier = Modifier.offset(x = 60.dp, y = 80.dp).size(20.dp))
        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700UL), modifier = Modifier.offset(x = 160.dp, y = 100.dp).size(40.dp))
        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700UL), modifier = Modifier.offset(x = 280.dp, y = 180.dp).size(50.dp))
        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700UL), modifier = Modifier.offset(x = 40.dp, y = 220.dp).size(45.dp))
        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700UL), modifier = Modifier.offset(x = 130.dp, y = 230.dp).size(15.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoPlaceholder()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Tenggelam Dalam Tidur Nyenyak.",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Tombol Get Started di bagian bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .wrapContentSize()
        ) {
            // Shadow block (warna gelap di belakang)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 0.dp, y = 8.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(DarkShadow)
            )

            // Tombol asli
            Button(
                onClick = onGetStartedClick,
                colors = ButtonDefaults.buttonColors(containerColor = LightBlueBtn),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(250.dp)
                    .height(64.dp)
            ) {
                Text(
                    text = "Get Started!",
                    color = Color(0xFF1D1B20UL),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}