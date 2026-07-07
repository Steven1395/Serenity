package com.example.serenity.uiux.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class AuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onResult(false, "Email dan password tidak boleh kosong")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val exception = task.exception
                    // Validasi pesan error saat pendaftaran akun baru
                    val errorMessage = when (exception) {
                        is FirebaseAuthUserCollisionException -> "Email ini sudah terdaftar! Silakan gunakan email lain atau langsung Log In."
                        else -> exception?.localizedMessage ?: "Pendaftaran gagal. Silakan coba lagi."
                    }
                    onResult(false, errorMessage)
                }
            }
    }

    fun logIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onResult(false, "Email dan password tidak boleh kosong")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val exception = task.exception
                    // --- MEMETAKAN ERROR FIREBASE KE BAHASA INDONESIA ---
                    val errorMessage = when (exception) {
                        // Jika email tidak ditemukan di database Firebase
                        is FirebaseAuthInvalidUserException -> "Email belum terdaftar! Silakan buat akun terlebih dahulu."

                        // Jika password yang dimasukkan salah atau format email aneh
                        is FirebaseAuthInvalidCredentialsException -> "Password salah! Periksa kembali kata sandi Anda."

                        // Error tidak terduga lainnya (misal: tidak ada internet)
                        else -> exception?.localizedMessage ?: "Login gagal. Silakan coba beberapa saat lagi."
                    }
                    onResult(false, errorMessage)
                }
            }
    }

    // --- FITUR BARU: LUPA PASSWORD ---
    fun resetPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isEmpty()) {
            onResult(false, "Email tidak boleh kosong")
            return
        }
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        // Validasi jika user meminta reset password tapi emailnya belum terdaftar
                        is FirebaseAuthInvalidUserException -> "Email ini belum terdaftar di aplikasi kami."
                        else -> exception?.localizedMessage ?: "Gagal mengirim email reset. Coba lagi."
                    }
                    onResult(false, errorMessage)
                }
            }
    }
}