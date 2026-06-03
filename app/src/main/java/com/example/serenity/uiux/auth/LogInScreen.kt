package com.example.serenity.uiux.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPurpleBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        LogoPlaceholder()
        Spacer(modifier = Modifier.height(32.dp))

        // Kartu Putih
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text("Email", color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Value", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderBtn,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Password", color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Value", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderBtn,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Handle Login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LavenderBtn),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Log In", color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Forgot password?",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "New to Serenity? Sign in here",
            color = Color.White,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline
        )
    }
}