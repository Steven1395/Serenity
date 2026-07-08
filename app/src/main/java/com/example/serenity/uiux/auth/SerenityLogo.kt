package com.example.serenity.uiux.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.serenity.R

val DarkPurpleBg = Color(0xFF312A5C)
val LightBlueBtn = Color(0xFFC0DAFA)
val LavenderBtn = Color(0xFF8884D8)
val DarkShadow = Color(0xFF1E193E)

@Composable
fun LogoPlaceholder() {
    Image(
        painter = painterResource(id = R.drawable.logo_serenity),
        contentDescription = "Logo Aplikasi Serenity",
        modifier = Modifier.size(160.dp)
    )
}