package com.example.serenity.uiux.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPurpleBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        LogoPlaceholder()

        Spacer(modifier = Modifier.height(24.dp))
        Text("Create an account", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Enter your email to sign up for this app", color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(24.dp))

        // Input Email (Putih polos)
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("email@domain.com", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Continue */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LavenderBtn),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Garis "or"
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.5f))
            Text("or", color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(horizontal = 16.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Google & Apple (Bisa pakai Icon bawaan/res resource)
        Button(
            onClick = { /* Google Login */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue with Google", color = Color.Black, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Apple Login */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue with Apple", color = Color.Black, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer Text
        val footerText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) { append("By clicking continue, you agree to our ") }
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Medium)) { append("Terms of Service") }
            withStyle(style = SpanStyle(color = Color.Gray)) { append("\nand ") }
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Medium)) { append("Privacy Policy") }
        }
        Text(text = footerText, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.padding(bottom = 16.dp))
    }
}