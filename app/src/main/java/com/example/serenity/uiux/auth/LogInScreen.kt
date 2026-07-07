package com.example.serenity.uiux.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToForgotPass: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State untuk mekanik buka/tutup mata password
    var passwordVisible by remember { mutableStateOf(false) }

    // State untuk manajemen validasi error
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Text("Email", fontFamily = PoppinsFont, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        // Reset status error saat user mulai mengetik
                        if (isEmailError) isEmailError = false
                        if (email.isNotBlank() && password.isNotBlank()) errorMessage = null
                    },
                    placeholder = { Text("Email", fontFamily = PoppinsFont, color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = isEmailError, // Mengaktifkan border merah jika error
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderBtn,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = Color.Red
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Password", fontFamily = PoppinsFont, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        // Reset status error saat user mulai mengetik
                        if (isPasswordError) isPasswordError = false
                        if (email.isNotBlank() && password.isNotBlank()) errorMessage = null
                    },
                    placeholder = { Text("Password", fontFamily = PoppinsFont, color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = isPasswordError, // Mengaktifkan border merah jika error
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderBtn,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = Color.Red
                    ),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Menampilkan pesan teks error jika validasi gagal (termasuk dari Firebase)
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontFamily = PoppinsFont,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        // Cek kondisi kosong sebelum memanggil fungsi login
                        isEmailError = email.isBlank()
                        isPasswordError = password.isBlank()

                        if (email.isBlank() && password.isBlank()) {
                            errorMessage = "Email dan Password tidak boleh kosong!"
                        } else if (email.isBlank()) {
                            errorMessage = "Email tidak boleh kosong!"
                        } else if (password.isBlank()) {
                            errorMessage = "Password tidak boleh kosong!"
                        } else {
                            // --- JIKA VALID, PANGGIL FIREBASE LOGIN ---
                            errorMessage = null // Bersihkan error sebelumnya

                            authManager.logIn(email, password) { success, msg ->
                                if (success) {
                                    Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess() // Pindah ke layar selanjutnya (Kuesioner/Dashboard)
                                } else {
                                    // Lempar pesan kegagalan dari Firebase ke teks UI
                                    errorMessage = msg ?: "Login gagal. Pastikan email dan password benar."
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LavenderBtn),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Log In", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Lupa password?",
                    fontFamily = PoppinsFont,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onNavigateToForgotPass() } // --- FUNGSI KLIK DITAMBAHKAN ---
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Baru di Serenity?",
                fontFamily = PoppinsFont,
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Sign up di sini",
                fontFamily = PoppinsFont,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onNavigateToSignIn() }
            )
        }
    }
}