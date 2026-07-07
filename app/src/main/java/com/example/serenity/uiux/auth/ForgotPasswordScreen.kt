package com.example.serenity.uiux.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onNavigateBackToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // State untuk menampung pesan error

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

                // Judul
                Text(
                    text = "Lupa Password",
                    fontFamily = PoppinsFont,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Deskripsi
                Text(
                    text = "Silakan masukkan alamat email Anda untuk menerima informasi atur ulang kata sandi.",
                    fontFamily = PoppinsFont,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Form Email
                Text("Masukkan alamat email", fontFamily = PoppinsFont, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        // Sembunyikan error saat user mulai mengetik ulang
                        if (errorMessage != null) errorMessage = null
                    },
                    placeholder = { Text("Email", fontFamily = PoppinsFont, color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = errorMessage != null, // Outline jadi merah jika ada error
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderBtn,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = Color.Red
                    ),
                    singleLine = true
                )

                // Menampilkan teks error merah jika validasi atau Firebase gagal
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontFamily = PoppinsFont,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol Request Reset
                Button(
                    onClick = {
                        if (email.isBlank()) {
                            errorMessage = "Email tidak boleh kosong!"
                        } else {
                            // Panggil fungsi resetPassword dari AuthManager
                            authManager.resetPassword(email) { success, msg ->
                                if (success) {
                                    // Munculkan notifikasi pop-up berhasil
                                    Toast.makeText(context, "Link reset berhasil dikirim! Silakan cek email Anda.", Toast.LENGTH_LONG).show()
                                    // Otomatis kembali ke halaman Login setelah berhasil
                                    onNavigateBackToLogin()
                                } else {
                                    // Tangkap dan tampilkan pesan error dari Firebase
                                    errorMessage = msg
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LavenderBtn),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Minta link atur ulang", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol Back To Login
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Kembali ke Login",
                        fontFamily = PoppinsFont,
                        color = LavenderBtn,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateBackToLogin() }
                    )
                }
            }
        }
    }
}