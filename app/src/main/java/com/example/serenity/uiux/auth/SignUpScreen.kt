package com.example.serenity.uiux.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(onContinueClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    // --- INISIALISASI AUTH & CONTEXT ---
    val authManager = remember { AuthManager() }
    val context = LocalContext.current

    val PoppinsFont = try { FontFamily(Font(R.font.poppins_regular, FontWeight.Normal), Font(R.font.poppins_bold, FontWeight.Bold)) } catch (_: Exception) { FontFamily.Default }

    Column(
        modifier = Modifier.fillMaxSize().background(DarkPurpleBg).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        LogoPlaceholder()

        Spacer(modifier = Modifier.height(24.dp))
        Text("Buat akun", fontFamily = PoppinsFont, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Masukkan Email dan Password untuk membuat akun", fontFamily = PoppinsFont, color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(24.dp))

        // --- FIELD INPUT EMAIL ---
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", fontFamily = PoppinsFont, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- FIELD INPUT PASSWORD ---
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", fontFamily = PoppinsFont, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
            ),
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility", tint = Color.Gray)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- TOMBOL CONTINUE DENGAN LOGIKA FIREBASE ---
        Button(
            onClick = {
                authManager.signUp(email, password) { success, errorMessage ->
                    if (success) {
                        Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                        onContinueClick() // Lanjut ke halaman berikutnya
                    } else {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LavenderBtn),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lanjut", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.5f))
            Text("atau", fontFamily = PoppinsFont, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(horizontal = 16.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* TODO: Google Login */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lanjut dengan Google", fontFamily = PoppinsFont, color = Color.Black, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* TODO: Apple Login */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lanjut dengan Apple", fontFamily = PoppinsFont, color = Color.Black, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.weight(1f))

        val footerText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) { append("Dengan mengklik Lanjut, Anda menyetujui\n") }
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Medium)) { append("Ketentuan Layanan") }
            withStyle(style = SpanStyle(color = Color.Gray)) { append(" dan ") }
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Medium)) { append("Kebijakan Privasi") }
        }
        Text(text = footerText, fontFamily = PoppinsFont, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.padding(bottom = 16.dp))
    }
}